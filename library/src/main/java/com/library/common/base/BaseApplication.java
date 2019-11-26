package com.library.common.base;

import android.app.Application;
import android.content.Context;

import com.library.common.IApp;
import com.library.common.base.delegate.AppDelegate;
import com.library.common.di.component.AppComponent;
import com.library.common.integration.IRepositoryManager;

import java.io.File;


/**
 * Description: Base Application <br>
 * <p>
 * 使用{@link AppDelegate }代理Application 和各Activity的生命周期 <br>
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/24 18:14 <br>
 */
public class BaseApplication extends Application implements IApp
{
    private AppDelegate mAppDelegate;

    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base context
     */
    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        this.mAppDelegate.onCreate(this);
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate()
    {
        super.onTerminate();
        if (mAppDelegate != null)
        {
            this.mAppDelegate.onTerminate();
        }
    }

    /**
     * 将AppComponent返回出去, AppComponent接口中声明的方法返回的实例,
     * 在getAppComponent()拿到对象后都可以直接使用
     */
    @Override
    public AppComponent getAppComponent()
    {
        return mAppDelegate.getAppComponent();
    }


    public IRepositoryManager getRepositoryManage()
    {
        return mAppDelegate.getAppComponent().repositoryManager();
    }

    public File getCacheFile()
    {
        return mAppDelegate.getAppComponent().cacheFile();
    }

}
