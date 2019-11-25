package com.library.common.di.module;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.library.common.LibraryConfig;
import com.library.common.network.RequestInterceptor;
import com.library.common.utils.FileUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/24 17:45 <br>
 */
@Module
public class ClientModule
{
    /**
     * @param builder
     * @param client
     * @param httpUrl
     * @return
     * @description:提供retrofit
     */
    @Singleton
    @Provides
    Retrofit provideRetrofit(Application application, @Nullable RetrofitConfiguration configuration,
                             Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl,
                             Gson gson)
    {
        builder.baseUrl(httpUrl)
               .client(client)
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
               .addConverterFactory(GsonConverterFactory.create(gson));//使用Gson

        if (configuration != null)
        {
            configuration.configRetrofit(application, builder);
        }
        return builder.build();
    }

    /**
     * 提供OkhttpClient
     *
     * @param builder
     * @return
     */
    @Singleton
    @Provides
    OkHttpClient provideClient(Application application, @Nullable OkHttpConfiguration configuration,
                               OkHttpClient.Builder builder, Interceptor intercept,
                               @Nullable List<Interceptor> interceptors, File cache)
    {
//        if(LibraryConfig.DEBUG){
//            if(LibraryConfig.BATE)
//                // bate
//                builder.sslSocketFactory(getSslContext(application).getSocketFactory(), getTrustAllCerts(application)[0]);
//            else
//                // debug
//                builder.sslSocketFactory(getSslSocketFactory(application));
//        }else {
//            // release
//            builder.sslSocketFactory(getSslContext(application).getSocketFactory(), getTrustAllCerts(application)[0]);
//        }

        builder.connectTimeout(LibraryConfig.APP_TIME_OUT, TimeUnit.SECONDS)
//               .sslSocketFactory(getSslSocketFactory(application))  //debug
//               .sslSocketFactory(getSslContext(application).getSocketFactory(), getTrustAllCerts(application)[0])
               .hostnameVerifier((hostname, session) -> {
                   if (LibraryConfig.DEBUG)
                   {
                       return true;
                   }
                   else
                   {
                       HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                       return hv.verify("*.qiandw.com", session);
                   }
               })
               .readTimeout(LibraryConfig.APP_TIME_OUT, TimeUnit.SECONDS)
               .cache(new Cache(cache, LibraryConfig.MAX_CACHE_SIZE)) //Retrofit cache
               .addNetworkInterceptor(intercept);

        if (LibraryConfig.DEBUG)
        {
            builder.sslSocketFactory(getSslSocketFactory(application));
        }
        else
        {
            if (Build.VERSION.SDK_INT > 19)
            {
                builder.sslSocketFactory(getSslContext(application).getSocketFactory(), getTrustAllCerts(application)[0]);
            }
        }


        if (interceptors != null)
        {
            //如果外部提供了interceptor的集合则遍历添加
            for (Interceptor interceptor : interceptors)
            {
                builder.addInterceptor(interceptor);
            }
        }

        if (configuration != null)
        {
            configuration.configOkHttp(application, builder);
        }

        //添加到最后一个，以便可以打印之前配置的Interceptor
        if (LibraryConfig.DEBUG)
        {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        return builder.build();
    }


    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder()
    {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder()
    {
        return new OkHttpClient.Builder();
    }


    @Singleton
    @Provides
    Interceptor provideInterceptor(RequestInterceptor intercept)
    {
        return intercept;//打印请求信息的拦截器
    }


    /**
     * 提供RXCache客户端
     *
     * @param cacheDirectory RxCache缓存路径
     * @return
     */
    @Singleton
    @Provides
    RxCache provideRxCache(Application application, @Nullable RxCacheConfiguration configuration,
                           @Named("RxCacheDirectory") File cacheDirectory)
    {
        RxCache.Builder builder = new RxCache.Builder();
        if (configuration != null)
        {
            configuration.configRxCache(application, builder);
        }
        return builder.persistence(cacheDirectory, new GsonSpeaker());
    }


    /**
     * 需要单独给RxCache提供缓存路径
     * 提供RxCache缓存地址
     */
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    File provideRxCacheDirectory(File cacheDir)
    {
        File cacheDirectory = new File(cacheDir, "RxCache");
        return FileUtil.makeDirs(cacheDirectory);
    }

    public interface RetrofitConfiguration
    {
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    public interface OkHttpConfiguration
    {
        void configOkHttp(Context context, OkHttpClient.Builder builder);
    }

    public interface RxCacheConfiguration
    {
        void configRxCache(Context context, RxCache.Builder builder);
    }

    private SSLSocketFactory getSslSocketFactory(Context context)
    {
        SSLContext sslContext = null;
        try
        {
            InputStream certificates = context.getAssets().open("keystore.p12");
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(certificates, "111111".toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            kmf.init(keyStore, "111111".toCharArray());
            KeyManager[] keyManagers = kmf.getKeyManagers();
            sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }

                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException
                {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }
            }
            };
            sslContext.init(keyManagers, trustAllCerts, null);



        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return sslContext != null? sslContext.getSocketFactory():null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private X509TrustManager[] getTrustAllCerts(Context context)
    {
        return new X509TrustManager[]{new X509TrustManager()
        {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
            {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
            {
                if (chain == null)
                {
                    throw new IllegalArgumentException("Check Server X509Certificate Is Null");
                }
                if (chain.length < 0)
                {
                    throw new IllegalArgumentException("Check Server X509Certificate Is Empty");
                }

                for (X509Certificate cert : chain)
                {
                    cert.checkValidity();
                    try
                    {
                        InputStream is = new BufferedInputStream(context.getAssets().open("ssl.pem"));
                        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                        X509Certificate serverCert = (X509Certificate) certificateFactory.generateCertificate(is);
                        cert.verify(serverCert.getPublicKey());
                    }
                    catch (NoSuchAlgorithmException e)
                    {
                        e.printStackTrace();
                    }
                    catch (InvalidKeyException e)
                    {
                        e.printStackTrace();
                    }
                    catch (NoSuchProviderException e)
                    {
                        e.printStackTrace();
                    }
                    catch (SignatureException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public X509Certificate[] getAcceptedIssuers()
            {
                return new X509Certificate[0];
            }
        }};
    }


    private SSLContext getSslContext(Context context)
    {
        SSLContext sslContext = null;
        try
        {
//            sslContext = SSLContext.getInstance("TLS");
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustAllCerts(context), null);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (KeyManagementException e)
        {
            e.printStackTrace();
        }
        return  sslContext;
    }
}
