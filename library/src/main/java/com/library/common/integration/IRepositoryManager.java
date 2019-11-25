package com.library.common.integration;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/31 11:28 <br>
 */

public interface IRepositoryManager
{

    /**
     * 根据传入的Class获取对应的Retrift service
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);

    /**
     * 根据传入的Class获取对应的RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    <T> T obtainCacheService(Class<T> cache);

}
