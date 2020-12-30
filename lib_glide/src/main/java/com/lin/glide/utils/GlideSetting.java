package com.lin.glide.utils;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * @author ：王文彬 on 2020-07-13 14：10
 * @describe：
 * @email：wangwenbin29@jd.com
 */
public class GlideSetting {

  @SuppressLint("StaticFieldLeak")
  private static Context context = null;


  private GlideSetting() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }

  /**
   * 在Application中进行初始化
   *
   * @param context 上下文
   */
  public static void init(Context context) {
    GlideSetting.context = context.getApplicationContext();
  }


  /**
   * 获取ApplicationContext
   *
   * @return ApplicationContext
   */
  public static Context getContext() {
    if (context != null) {
      return context;
    } else {
      throw new NullPointerException("u should init first");
    }
  }

}