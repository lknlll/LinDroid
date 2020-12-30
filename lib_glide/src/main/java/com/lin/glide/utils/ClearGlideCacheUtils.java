package com.lin.glide.utils;

import android.content.Context;
import android.os.Looper;

import com.bumptech.glide.Glide;
import com.lin.glide.config.GlideConfig;

import java.io.File;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author ：王文彬 on 2018/5/24 17：41
 * @describe： 清除Glide缓存工具类
 * @email：wwb199055@126.com
 */
public class ClearGlideCacheUtils {

  private Context thisContext = GlideSetting.getContext();

  private ClearGlideCacheUtils() {
  }

  private static volatile ClearGlideCacheUtils instance;

  public static ClearGlideCacheUtils getInstance() {
    if (instance == null) {
      synchronized (ClearGlideCacheUtils.class) {
        if (instance == null) {
          instance = new ClearGlideCacheUtils();
        }
      }
    }
    return instance;
  }


  /**
   * 获取Glide磁盘缓存大小
   */
  public String getCacheSize() {
    try {
      return GlideFormatSizeUtils.getFormatSize(GlideFileUtils.getFolderSize(new File(thisContext.getCacheDir() + "/" +
          GlideConfig.getMemoryCachePath())), 2);
    } catch (Exception e) {
      e.printStackTrace();
      return "获取失败";
    }
  }


  /**
   * 清除Glide磁盘缓存，自己获取缓存文件夹并删除
   */
  public boolean cleanCatchDisk() {
    return GlideFileUtils.deleteFolderFile(thisContext.getCacheDir() + "/" + GlideConfig.getMemoryCachePath(), true);
  }


  /**
   * 清除图片磁盘缓存，调用Glide自带方法
   */
  public boolean clearDiskCache() {
    try {

      if (Looper.myLooper() == Looper.getMainLooper()) {
        Flowable.create(emitter -> Glide.get(thisContext).clearDiskCache(), BackpressureStrategy.BUFFER)
            .subscribeOn(Schedulers.io())
            .subscribe();

      } else {
        Glide.get(thisContext).clearDiskCache();
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 清除Glide内存缓存
   */
  public boolean clearCacheMemory() {
    try {
      //只能在主线程执行
      if (Looper.myLooper() == Looper.getMainLooper()) {
        Glide.get(thisContext).clearMemory();
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}

