package com.library.common.integration;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.library.common.base.delegate.AppDelegate;
import com.library.common.di.module.GlobalConfigModule;

import java.util.List;


/**
 * Description: 配置App全局参数 <br>
 * <p>
 * 必须在Manifest.xml声明其实现类<? extends ConfigModule> <br>
 * <p>
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/25 10:23 <br>
 */

public interface ConfigModule
{
    /**
     * 使用{@link GlobalConfigModule.Builder}给框架配置一些配置参数
     *
     * @param context
     * @param builder
     */
    void applyOptions(Context context, GlobalConfigModule.Builder builder);

    /**
     * 使用{@link AppDelegate.Lifecycle}在Application的生命周期中注入一些操作
     */
    void injectAppLifecycle(Context context, List<AppDelegate.Lifecycle> lifecycles);

    /**
     * 使用{@link Application.ActivityLifecycleCallbacks}在Activity的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    void injectActivityLifecycle(Context context,
                                 List<Application.ActivityLifecycleCallbacks> lifecycles);


    /**
     * 使用{@link FragmentManager.FragmentLifecycleCallbacks}在Fragment的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles);
}
