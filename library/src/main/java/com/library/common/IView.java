package com.library.common;

import android.content.Context;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/11/8 18:07 <br>
 */
public interface IView
{
    /**
     * show loading
     */
    void showLoading();

    /**
     * show content
     */
    void showContent();

    /**
     * show no network
     */
    void showNoNetwork();

    /**
     * show error
     */
    void showError(String msg);

    /**
     * getHostContext
     */
    Context getHostContext();

}
