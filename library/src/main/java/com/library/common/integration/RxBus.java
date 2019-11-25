package com.library.common.integration;


import com.library.common.utils.RxUtil;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;


/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/8/2 16:48 <br>
 */
public class RxBus
{
    private final FlowableProcessor<Object> bus;

    private RxBus()
    {
        // Subject同时充当了Observer和Observable的角色，Subject是非线程安全的，
        // 需要将 Subject转换为一个 SerializedSubject
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getInstance()
    {
        return RxBusHolder.sInstance;
    }

    private static class RxBusHolder
    {
        private static final RxBus sInstance = new RxBus();
    }


    /**
     * 发送消息
     *
     * @param o
     */
    public void post(Object o)
    {
        // PublishSubject只会把在订阅发生的时间点之后来自原始Flowable的数据发射给观察者
        if (bus.hasSubscribers())
        {
            bus.onNext(o);
        }
    }


    /**
     * 接收消息
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Flowable<T> onEvent(Class<T> eventType)
    {
        return bus.ofType(eventType);
    }

    // 封装默认订阅
    public <T> Disposable onEvent(Class<T> eventType, Consumer<T> act)
    {
        return bus.ofType(eventType).compose(RxUtil.flowableTransformer()).subscribe(act);
    }
}
