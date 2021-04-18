package com.library.common;


import android.os.Bundle;
import android.view.View;

import com.library.common.di.component.AppComponent;

import androidx.fragment.app.FragmentManager;

/**
 * Description: {BaseActivity}实现该接口 <br>
 *
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/25 10:36 <br>
 */

public interface IActivity
{

    /**
     * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
     *
     * @param appComponent appComponent
     */
    void setupActivityComponent(AppComponent appComponent);


    /*
     * 如果initView返回0,框架则不会调用{@link android.app.Activity#setContentView(int)}
     *
     * @deprecated 使用ViewBind代替
     * @see #setBindingView()
     */
    // int setContentLayout(Bundle savedInstanceState);


    /**
     * 使用ViewBinding绑定视图
     * @return 布局View
     */
    View setBindingView();

    /**
     * 初始化Activity相关操作
     * @param savedInstanceState savedInstanceState
     */
    void initActivity(Bundle savedInstanceState);

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {BaseFragment}
     * 的Fragment将不起任何作用
     */
    boolean useFragment();
}
