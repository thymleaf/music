package com.library.common.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.library.common.integration.IRepositoryManager;
import com.library.common.integration.RepositoryManager;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/24 16:40 <br>
 */
@Module
public class AppModule
{
    private Application mApplication;

    public AppModule(Application application)
    {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public Application provideApplication()
    {
        return mApplication;
    }

    @Singleton
    @Provides
    public Gson provideGson(Application application, @Nullable GsonConfiguration configuration)
    {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null)
        {
            configuration.configGson(application, builder);
        }
        return builder.create();
    }

    @Singleton
    @Provides
    public IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager)
    {
        return repositoryManager;
    }

    @Singleton
    @Provides
    public Map<String, Object> provideExtras()
    {
        return new ArrayMap<>();
    }

    public interface GsonConfiguration
    {
        void configGson(Context context, GsonBuilder builder);
    }

}
