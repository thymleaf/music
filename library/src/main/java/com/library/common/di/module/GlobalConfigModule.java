package com.library.common.di.module;

import android.app.Application;
import android.text.TextUtils;

import com.library.common.network.BaseUrl;
import com.library.common.network.GlobalHttpHandler;
import com.library.common.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;


/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/24 17:20 <br>
 */
@Module
public class GlobalConfigModule
{
    private HttpUrl mApiUrl;
    private BaseUrl mBaseUrl;
    private GlobalHttpHandler mHandler;
    private List<Interceptor> mInterceptors;
    private File mCacheFile;
    private ClientModule.RetrofitConfiguration mRetrofitConfiguration;
    private ClientModule.OkHttpConfiguration mOkhttpConfiguration;
    private ClientModule.RxCacheConfiguration mRxCacheConfiguration;
    private AppModule.GsonConfiguration mGsonConfiguration;


    private GlobalConfigModule(Builder builder)
    {
        this.mApiUrl = builder.apiUrl;
        this.mBaseUrl = builder.baseUrl;
        this.mHandler = builder.handler;
        this.mInterceptors = builder.interceptors;
        this.mCacheFile = builder.cacheFile;
        this.mRetrofitConfiguration = builder.retrofitConfiguration;
        this.mOkhttpConfiguration = builder.okhttpConfiguration;
        this.mRxCacheConfiguration = builder.rxCacheConfiguration;
        this.mGsonConfiguration = builder.gsonConfiguration;
    }

    public static Builder builder()
    {
        return new Builder();
    }


    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideInterceptors()
    {
        return mInterceptors;
    }


    @Singleton
    @Provides
    HttpUrl provideBaseUrl()
    {
        if (mBaseUrl != null)
        {
            HttpUrl httpUrl = mBaseUrl.url();
            if (httpUrl != null)
            {
                return httpUrl;
            }
        }
        return mApiUrl == null ? HttpUrl.parse("https://api.github.com/") : mApiUrl;
    }


    @Singleton
    @Provides
    @Nullable
    GlobalHttpHandler provideGlobalHttpHandler()
    {
        return mHandler;//处理Http请求和响应结果
    }

    /**
     * 提供缓存文件
     */
    @Singleton
    @Provides
    File provideCacheFile(Application application)
    {
        return mCacheFile == null ? FileUtil.getCacheFile(application) : mCacheFile;
    }


    @Singleton
    @Provides
    @Nullable
    ClientModule.RetrofitConfiguration provideRetrofitConfiguration()
    {
        return mRetrofitConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.OkHttpConfiguration provideOkhttpConfiguration()
    {
        return mOkhttpConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.RxCacheConfiguration provideRxCacheConfiguration()
    {
        return mRxCacheConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    AppModule.GsonConfiguration provideGsonConfiguration()
    {
        return mGsonConfiguration;
    }


    public static final class Builder
    {
        private HttpUrl apiUrl;
        private BaseUrl baseUrl;
        private GlobalHttpHandler handler;
        private List<Interceptor> interceptors;
        private File cacheFile;
        private ClientModule.RetrofitConfiguration retrofitConfiguration;
        private ClientModule.OkHttpConfiguration okhttpConfiguration;
        private ClientModule.RxCacheConfiguration rxCacheConfiguration;
        private AppModule.GsonConfiguration gsonConfiguration;

        private Builder()
        {
        }

        public Builder baseUrl(String baseUrl)
        {
            if (TextUtils.isEmpty(baseUrl))
            {
                throw new IllegalArgumentException("BaseUrl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(BaseUrl baseUrl)
        {
            if (baseUrl == null)
            {
                throw new IllegalArgumentException("BaseUrl can not be null");
            }
            this.baseUrl = baseUrl;
            return this;
        }


        public Builder globalHttpHandler(GlobalHttpHandler handler)
        {
            //用来处理http响应结果
            this.handler = handler;
            return this;
        }

        /**
         * add interceptor, you can call this method multiple times
         *
         * @param interceptor
         * @return
         */
        public Builder addInterceptor(Interceptor interceptor)
        {
            //动态添加任意个interceptor
            if (interceptors == null)
            {
                interceptors = new ArrayList<>();
            }
            this.interceptors.add(interceptor);
            return this;
        }


        public Builder cacheFile(File cacheFile)
        {
            this.cacheFile = cacheFile;
            return this;
        }

        public Builder retrofitConfiguration(
                ClientModule.RetrofitConfiguration retrofitConfiguration)
        {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okhttpConfiguration(ClientModule.OkHttpConfiguration okhttpConfiguration)
        {
            this.okhttpConfiguration = okhttpConfiguration;
            return this;
        }

        public Builder rxCacheConfiguration(ClientModule.RxCacheConfiguration rxCacheConfiguration)
        {
            this.rxCacheConfiguration = rxCacheConfiguration;
            return this;
        }

        public Builder gsonConfiguration(AppModule.GsonConfiguration gsonConfiguration)
        {
            this.gsonConfiguration = gsonConfiguration;
            return this;
        }


        public GlobalConfigModule build()
        {
            return new GlobalConfigModule(this);
        }

    }

}
