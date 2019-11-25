package com.library.common.integration;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * Description: 数据请求层，包括网络请求&缓存&数据库等 <br>
 * <p>
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/31 15:45 <br>
 */
@Singleton
public class RepositoryManager implements IRepositoryManager
{
    private Lazy<Retrofit> mRetrofit;
    private Lazy<RxCache> mRxCache;
    private final Map<String, Object> mRetrofitService = new HashMap<>();
    private final Map<String, Object> mCacheServiceCache = new HashMap<>();

    @Inject
    public RepositoryManager(Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache)
    {
        this.mRetrofit = retrofit;
        this.mRxCache = rxCache;
    }

    /**
     * 根据传入的Class获取对应的Retrift service
     *
     * @param service
     * @param <T>
     * @return
     */
    @Override
    public <T> T obtainRetrofitService(Class<T> service)
    {
        T retrofitService;
        synchronized (mRetrofitService)
        {
            retrofitService = (T) mRetrofitService.get(service.getName());
            if (retrofitService == null)
            {
                retrofitService = mRetrofit.get()
                                           .create(service);
                mRetrofitService.put(service.getName(), retrofitService);
            }
        }
        return retrofitService;
    }

    /**
     * 根据传入的Class获取对应的RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    @Override
    public <T> T obtainCacheService(Class<T> cache)
    {
        T cacheService;
        synchronized (mCacheServiceCache)
        {
            cacheService = (T) mCacheServiceCache.get(cache.getName());
            if (cacheService == null)
            {
                cacheService = mRxCache.get()
                                       .using(cache);
                mCacheServiceCache.put(cache.getName(), cacheService);
            }
        }
        return cacheService;
    }
}
