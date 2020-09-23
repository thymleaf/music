package com.thymleaf.example.test;


import com.library.common.base.BaseResponse;


import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeService
{
    @GET("mob/message/page")
    Flowable<BaseResponse<NewsList>> getNews(@Query("newType") String newType);


    @GET("mob/message/page")
    Flowable<BaseResponse<NewsList>> getNews(@Query("newType") String newType,
                                             @Query("page") String page,
                                             @Query("rows") String rows);


}
