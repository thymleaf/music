package com.library.common.base.delegate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.library.common.di.component.AppComponent;

/**
 *
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/25 10:56 <br>
 */

public interface IFragment {
    /**
     * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
     * @param appComponent appComponent
     */
    void setupFragmentComponent(AppComponent appComponent);

    /*
     * @deprecated 替换成ViewBinding
     * @see #setViewBinding(ViewBinding)
     */
    //View setContentLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    /**
     *
     * @return viewBinding
     */
    ViewBinding setViewBinding();

    void initFragment(Bundle savedInstanceState);

    /*
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     *
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */
//    void setData(Object data);
}
