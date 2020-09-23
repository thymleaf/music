package com.library.common.utils;


import com.library.common.base.BaseResponse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/11/13 14:22 <br>
 */
public class HttpResponseUtil
{
    public static <T> FlowableTransformer<BaseResponse<T>, T> transformer()
    {
        return upstream -> upstream.flatMap(
                (Function<BaseResponse<T>, Flowable<T>>) baseResponse -> {
                    if (baseResponse.isSuccess())
                    {
                        return createData(baseResponse.getData());
                    }
                    else
                    {
                        return Flowable.error(
                                new ApiException(baseResponse.getMsg(), baseResponse.getCode(),
                                                 baseResponse.isUnauthorized()));
                    }
                });
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return flowable
     */
    public static <T> Flowable<T> createData(final T t)
    {
        return Flowable.create(emitter -> {
            try
            {
                emitter.onNext(t);
                emitter.onComplete();
            }
            catch (Exception e)
            {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
    }
}
