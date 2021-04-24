package com.thymleaf.music;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.library.common.LibraryConfig;
import com.library.common.base.delegate.ActivityLifecycle;
import com.library.common.base.delegate.AppDelegate;
import com.library.common.di.module.AppModule;
import com.library.common.di.module.ClientModule;
import com.library.common.di.module.GlobalConfigModule;
import com.library.common.integration.ConfigModule;
import com.library.common.network.GlobalHttpHandler;
import com.library.common.widget.swipebacklayout.SwipeBackManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.MultiDex;
import io.rx_cache2.internal.RxCache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import timber.log.Timber;


/**
 * Description: app 的全局配置信息,必须将此实现类声明到 AndroidManifest 中 <br>
 * <p>
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/24 16:03 <br>
 */
public class GlobalConfiguration implements ConfigModule
{

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder)
    {
        /**如果 BaseUrl 在 IApp 启动时不能确定,需要请求服务器接口动态获取,请使用以下代码
         并且使用 Okhttp (AppComponent中提供) 请求服务器获取到正确的 BaseUrl 后赋值给 GlobalConfiguration.sDomain
         切记整个过程必须在第一次调用 Retrofit 接口之前完成,如果已经调用过 Retrofit 接口,将不能动态切换 BaseUrl
         //                .baseUrl(new BaseUrl() {
         //                    @Override
         //                    public HttpUrl url() {
         //                        return HttpUrl.parse(sDomain);
         //                    }
         //                })
         */
        builder.baseUrl(AppConfig.APP_DOMAIN).globalHttpHandler(new GlobalHttpHandler()
        {
            //这里可以提供一个全局处理Http请求和响应结果的处理类,
            //这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
            @Override
            public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain,
                                                 Response response)
            {

                     /* 这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
                        注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
                        create a new request and modify it accordingly using the new token
                        Request newRequest = chain.request().newBuilder().header("token", newToken)
                                             .build();

                        retry the request

                        response.body().close();
                        如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可
                        如果不需要返回新的结果,则直接把response参数返回出去 */

                return response;
            }

            // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token
            // 或者header以及参数加密等操作
            @Override
            public Request onHttpRequestBefore(Interceptor.Chain chain, Request request)
            {
                    /*如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的requeat如增加header,不做操作则直接返回request参数
                      return chain.request().newBuilder().header("token", tokenId)
                             .build();
                    */
                return request;
            }
        }).gsonConfiguration(new AppModule.GsonConfiguration()
        {
            @Override
            public void configGson(Context context, GsonBuilder gsonBuilder)
            {
                //这里可以自己自定义配置Gson的参数
                gsonBuilder.serializeNulls()//支持序列化null的参数
                           .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
            }
        }).retrofitConfiguration(new ClientModule.RetrofitConfiguration()
        {
            @Override
            public void configRetrofit(Context context, Retrofit.Builder retrofitBuilder)
            {
                //这里可以自己自定义配置Retrofit的参数,甚至你可以替换系统配置好的okhttp对象
                // retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用fastjson替代gson
            }
        }).okhttpConfiguration(new ClientModule.OkHttpConfiguration()
        {
            @Override
            public void configOkHttp(Context context, OkHttpClient.Builder okHttpBuilder)
            {
                //这里可以自己自定义配置Okhttp的参数
                okHttpBuilder.writeTimeout(LibraryConfig.APP_TIME_OUT, TimeUnit.SECONDS);
            }
        }).addInterceptor(new Interceptor()
        {
            @Override
            public Response intercept(Chain chain) throws IOException
            {
                Request original = chain.request();
                Request request = original.newBuilder()
                                          //#header()更新头部信息
                                          //#addHeader()添加头部信息
                                          //.header("User-Agent", "Your-App-Name")  // 添加静态请求头部
                                          //.header("Accept", "application/vnd.yourapi.v1.full+json")
                                          //.method(original.method(), original.body())
//                                          .addHeader("x-token", TextUtils.isEmpty(AppUtil.getToken(context)) ? "-1" : AppUtil.getToken(context))
//                                          .addHeader("x-model", EncryptUtils.encryptMD5ToString(DeviceUtils.getModel()))
//                                          .addHeader("x-app-version",String.valueOf(DeviceUtils.getAppVersionName()))
                                          .addHeader("x-api-version","3.0")
                                          .addHeader("x-client-platform", "and").build();
                /**
                 * 如果需要添加动态请求头部，可通过以下两种方法：
                 *
                 public interface Service
                 {
                 // 1、=======================================
                 @Headers({"Accept: application/...","User-Agent: ..."})
                 @GET(...) request method(...);

                 // 2、=======================================
                 @GET(...) request method(@Header("Content-Range") String content);
                 }
                 */
                return chain.proceed(request);
            }
        }).rxCacheConfiguration(new ClientModule.RxCacheConfiguration()
        {
            @Override
            public void configRxCache(Context context, RxCache.Builder rxCacheBuilder)
            {
                //这里可以自己自定义配置RxCache的参数
                rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
            }
        });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppDelegate.Lifecycle> lifecycles)
    {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,
        // 所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppDelegate.Lifecycle()
        {

            @Override
            public void attachBaseContext(Context base)
            {
                //这里比 Application#onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
                MultiDex.install(base);
            }

            @Override
            public void onCreate(Application application)
            {
                if (LibraryConfig.DEBUG)
                {
                    //Timber初始化
                    //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
                    //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
                    //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
                    Timber.plant(new Timber.DebugTree());
                    // 如果想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展
                    // Logger.addLogAdapter(new AndroidLogAdapter());
                    // Timber.plant(new Timber.DebugTree() {
                    //   @Override
                    //   protected void log(int priority, String tag, String message, Throwable t) {
                    //       Logger.log(priority, tag, message, t);
                    //    }
                    // });

                }

                //在Application#onCreate中初始化
                SwipeBackManager.getInstance().init(application);

                //leakCanary内存泄露检查
//                ((IApp) application).getAppComponent().extras().put(RefWatcher.class.getName(),
//                        LibraryConfig.IS_USE_CANARY ? LeakCanary
//                                .install(application) : RefWatcher.DISABLED);
            }

            @Override
            public void onTerminate(Application application)
            {

            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context,
                                        List<Application.ActivityLifecycleCallbacks> lifecycles)
    {
        /**
         * 添加{@link Application.ActivityLifecycleCallbacks}对象监听Activity 生命周期<br>
         *
         * @Note: 此处实例化对象是作为 {@link ActivityLifecycle}
         * 的补充和扩展，其对应的方法在 {@link ActivityLifecycle}
         * 后面执行
         *
         * @see {@link ActivityLifecycle}
         */
        lifecycles.add(new Application.ActivityLifecycleCallbacks()
        {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState)
            {

            }

            @Override
            public void onActivityStarted(final Activity activity)
            {
                if (!activity.getIntent().getBooleanExtra("isInitToolbar", false))
                {
                    //由于加强框架的兼容性,故将 setContentView 放到 onActivityCreated 之后,
                    // onActivityStarted 之前执行，而 findViewById 必须在
                    // Activity setContentView() 后才有效,所以将以下代码从
                    // 之前的 onActivityCreated 中移动到 onActivityStarted
                    // 中执行
                    activity.getIntent().putExtra("isInitToolbar", true);
                    //这里全局给Activity设置toolbar和title,你想象力有多丰富,
                    //这里就有多强大,以前放到BaseActivity的操作都可以放到这里
                    if (activity.findViewById(R.id.toolbar) != null)
                    {
                        if (activity instanceof AppCompatActivity)
                        {
                            ((AppCompatActivity) activity).setSupportActionBar(activity.findViewById(R.id.toolbar));
                            ((AppCompatActivity) activity).getSupportActionBar()
                                                          .setDisplayShowTitleEnabled(false);
                        }
                        else
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            {
                                activity.setActionBar(activity.findViewById(R.id.toolbar));
                                activity.getActionBar().setDisplayShowTitleEnabled(false);
                            }
                        }
                    }
                    if (activity.findViewById(R.id.toolbar_title) != null)
                    {
                        ((TextView) activity.findViewById(R.id.toolbar_title))
                                .setText(activity.getTitle());
                    }
                    if (activity.findViewById(R.id.toolbar_back) != null)
                    {
                        TextView back = activity.findViewById(R.id.toolbar_back);


                        back.setOnClickListener(v -> activity.onBackPressed());

                    }
                }
            }

            @Override
            public void onActivityResumed(Activity activity)
            {
                Timber.w(activity + " GlobalConfiguration - onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity)
            {
                Timber.w(activity + " GlobalConfiguration - onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity)
            {
                Timber.w(activity + " GlobalConfiguration - onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState)
            {
                Timber.w(activity + " GlobalConfiguration - onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity)
            {
                Timber.w(activity + " GlobalConfiguration - onActivityDestroyed");
            }
        });
    }

    @Override
    public void injectFragmentLifecycle(Context context,
                                        List<FragmentManager.FragmentLifecycleCallbacks> lifecycles)
    {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks()
        {

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState)
            {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建是重复利用已经创建的Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 如果在 XML 中使用 <Fragment/> 标签的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则 setRetainInstance(true) 无效
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
//                f.setRetainInstance(true);
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f)
            {
                //这里应该是检测 Fragment 而不是 FragmentLifecycleCallbacks 的泄露。
//                ((RefWatcher) ((IApp) f.getActivity().getApplication()).getAppComponent().extras()
//                                                                       .get(RefWatcher.class
//                                                                               .getName()))
//                        .watch(f);
            }
        });
    }


//    private String convertStatusCode(HttpException httpException)
//    {
//        String msg;
//        if (httpException.code() == 500)
//        {
//            msg = "服务器发生错误";
//        }
//        else if (httpException.code() == 404)
//        {
//            msg = "请求地址不存在";
//        }
//        else if (httpException.code() == 403)
//        {
//            msg = "请求被服务器拒绝";
//        }
//        else if (httpException.code() == 307)
//        {
//            msg = "请求被重定向到其他页面";
//        }
//        else
//        {
//            msg = httpException.message();
//        }
//        return msg;
//    }

}
