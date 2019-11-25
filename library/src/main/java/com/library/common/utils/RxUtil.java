package com.library.common.utils;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/8/11 11:46 <br>
 */
public class RxUtil
{
    /**
     * {@link Flowable}统一线程处理
     *
     * @param <T>
     * @return {@link FlowableTransformer} instance
     */
    public static <T> FlowableTransformer<T, T> flowableTransformer()
    {
        //compose简化线程
        return observable -> observable.subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * {@link Observable}统一线程处理
     *
     * @param <T>
     * @return {@link ObservableTransformer} instance
     */
    public static <T> ObservableTransformer<T, T> observableTransformer()
    {
        //compose简化线程
        return observable -> observable.subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread());
    }
}
