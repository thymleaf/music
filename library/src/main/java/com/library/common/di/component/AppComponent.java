package com.library.common.di.component;

import android.app.Application;

import com.library.common.base.delegate.AppDelegate;
import com.library.common.di.module.AppModule;
import com.library.common.di.module.ClientModule;
import com.library.common.di.module.GlobalConfigModule;
import com.library.common.integration.AppManager;
import com.library.common.integration.IRepositoryManager;

import java.io.File;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;


@Singleton
@Component(modules = {AppModule.class, ClientModule.class, GlobalConfigModule.class})
public interface AppComponent
{
    Application application();

    //用于管理网络请求层,以及数据缓存层
    IRepositoryManager repositoryManager();

    OkHttpClient okHttpClient();

    //缓存文件根目录(RxCache和Glide的的缓存都已经作为子文件夹在这个目录里),应该将所有缓存放到这个根目录里,
    //便于管理和清理,可在GlobeConfigModule里配置
    File cacheFile();

    //用于管理所有activity
    AppManager appManager();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Map<String, Object> extras();

    void inject(AppDelegate delegate);
}
