package com.lin.glide.config;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * @author ：王文彬 on 2020-07-13 11：26
 * @describe ：
 * @email ：wangwenbin29@jd.com
 */
@GlideModule
public class GlideCacheConfiguration extends AppGlideModule {
  @Override
  public void applyOptions(Context context, GlideBuilder builder) {

    builder.setMemoryCache(new LruResourceCache(GlideConfig.getMemoryCacheSize()));

    builder.setDiskCache(new InternalCacheDiskCacheFactory(context, GlideConfig.getMemoryCachePath(), GlideConfig
        .getMemoryCacheSize()));
  }

  @Override
  public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
    registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(GlideOkHttpConfig.getOkHttpClient()));
  }

}
