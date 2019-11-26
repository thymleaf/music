package com.library.common.base.delegate;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import com.bumptech.glide.Glide;
import com.library.common.IApp;
import com.library.common.di.component.AppComponent;
import com.library.common.di.component.DaggerAppComponent;
import com.library.common.di.module.AppModule;
import com.library.common.di.module.ClientModule;
import com.library.common.di.module.GlobalConfigModule;
import com.library.common.integration.ConfigModule;
import com.library.common.integration.ManifestParser;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Description: AppDelegate可以代理Application的生命周期,在对应的生命周期,执行对应的逻辑 <br>
 * <p>
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/24 15:33 <br>
 */
public class AppDelegate implements IApp
{
    private Application mApplication;
    private AppComponent mAppComponent;
    @Inject
    protected ActivityLifecycle mActivityLifecycle;
    private final List<ConfigModule> mModules;
    private List<Lifecycle> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();
    private ComponentCallbacks2 mComponentCallback;

    public AppDelegate(Context context)
    {
        this.mModules = new ManifestParser(context).parse();
        for (ConfigModule module : mModules)
        {
            /**
             * Description: 代理Application生命周期 <br>
             *
             * @see GlobalConfiguration
             */
            module.injectAppLifecycle(context, mAppLifecycles);
            module.injectActivityLifecycle(context, mActivityLifecycles);
        }
    }

    public void attachBaseContext(Context base)
    {
        for (Lifecycle lifecycle : mAppLifecycles)
        {
            lifecycle.attachBaseContext(base);
        }
    }


    public void onCreate(Application application)
    {
        this.mApplication = application;
        mAppComponent = DaggerAppComponent.builder()
                                          .appModule(new AppModule(mApplication))//提供application
                                          .clientModule(new ClientModule())//用于提供okhttp和retrofit的单例
                                          .globalConfigModule(getGlobalConfigModule(mApplication, mModules))//全局配置
                                          .build();
        mAppComponent.inject(this);

        mAppComponent.extras()
                     .put(ConfigModule.class.getName(), mModules);

        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles)
        {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }

        for (Lifecycle lifecycle : mAppLifecycles)
        {
            lifecycle.onCreate(mApplication);
        }

        mComponentCallback = new AppComponentCallbacks(mApplication, mAppComponent);

        mApplication.registerComponentCallbacks(mComponentCallback);

    }


    public void onTerminate()
    {
        if (mActivityLifecycle != null)
        {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mComponentCallback != null)
        {
            mApplication.unregisterComponentCallbacks(mComponentCallback);
        }
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0)
        {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles)
            {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }
        if (mAppLifecycles != null && mAppLifecycles.size() > 0)
        {
            for (Lifecycle lifecycle : mAppLifecycles)
            {
                lifecycle.onTerminate(mApplication);
            }
        }
        this.mAppComponent = null;
        this.mActivityLifecycle = null;
        this.mActivityLifecycles = null;
        this.mComponentCallback = null;
        this.mAppLifecycles = null;
        this.mApplication = null;
    }


    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明{@link ConfigModule}的实现类,和Glide的配置方式相似
     *
     * @return
     */
    private GlobalConfigModule getGlobalConfigModule(Context context, List<ConfigModule> modules)
    {

        GlobalConfigModule.Builder builder = GlobalConfigModule.builder();

        for (ConfigModule module : modules)
        {
            module.applyOptions(context, builder);
        }

        return builder.build();
    }


    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,
     * 在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    @Override
    public AppComponent getAppComponent()
    {
        return mAppComponent;
    }


    public interface Lifecycle
    {
        void attachBaseContext(Context base);

        void onCreate(Application application);

        void onTerminate(Application application);
    }

    private static class AppComponentCallbacks implements ComponentCallbacks2
    {
        private Application mApplication;
        private AppComponent mAppComponent;

        private AppComponentCallbacks(Application application, AppComponent appComponent)
        {
            this.mApplication = application;
            this.mAppComponent = appComponent;
        }

        @Override
        public void onTrimMemory(int level)
        {

        }

        @Override
        public void onConfigurationChanged(Configuration newConfig)
        {

        }

        @Override
        public void onLowMemory()
        {
            new Thread(() -> Glide.get(mApplication)
                    .clearDiskCache()).start();
        }
    }

}

