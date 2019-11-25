package com.library.common.integration;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;


/**
 * Description: Glide Configuration <br>
 *     
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/7/26 16:33 <br>
 */
@GlideModule
public class AppGlideModuleImpl extends AppGlideModule
{

    @Override
    public boolean isManifestParsingEnabled()
    {
        return false;
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder)
    {
        // 使用默认的 Memory Cache
        /**
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        */

        /**
         * By default Glide uses the InternalCacheDiskCacheFactory class to build disk caches
         * The default disk cache size is 250 MB and is placed in a specific directory
         * in the Application’s cache folder.
         *
         * @see InternalDiskCacheFactory(context, "cacheFolderName", diskCacheSizeBytes)
         */

        int diskCacheSizeBytes = 1024 * 1024 * 250; // 250 MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
    }


    @Override
    public void registerComponents(Context context, Glide glide, Registry registry)
    {
        registry.append(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
