package com.library.common;


import android.os.Bundle;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;

import com.library.common.di.component.AppComponent;

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
     * @param appComponent
     */
    void setupActivityComponent(AppComponent appComponent);


    /**
     * 如果initView返回0,框架则不会调用{@link android.app.Activity#setContentView(int)}
     *
     * @param savedInstanceState
     * @return layout
     */
    int setContentLayout(Bundle savedInstanceState);

    void initActivity(Bundle savedInstanceState);

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {BaseFragment}
     * 的Fragment将不起任何作用
     *
     * @return
     */
    boolean useFragment();
}
