package com.library.common.network;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/9/19 16:09 <br>
 */
public interface GlobalHttpHandler
{
    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

    GlobalHttpHandler EMPTY = new GlobalHttpHandler()
    {
        @Override
        public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain,
                                             Response response)
        {
            //不管是否处理,都必须将response返回出去
            return response;
        }

        @Override
        public Request onHttpRequestBefore(Interceptor.Chain chain, Request request)
        {
            //不管是否处理,都必须将request返回出去
            return request;
        }
    };

}
