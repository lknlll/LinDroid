package com.lin.glide.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.lin.glide.config.GlideApp;
import com.lin.glide.config.GlideCircleTransform;
import com.lin.glide.config.GlideRequest;
import com.lin.glide.config.RoundedRectanglesTransformation;

/**
 * @author ：王文彬 on 2020-07-13 14：10
 * @describe： 图片显示工具类，是基于{@link Glide 4}版本进行的封装,
 * 不使用全局{@link Context}，因为glide相较于其他图片显示框架，他的生命周期可以跟当前{@link android.app.Activity}、
 * {@link Fragment}或者当前{@link android.view.View}的生命周期保持一致，这是它的优点
 * @email：wangwenbin29@jd.com
 */
public class GlideUtils {


  /**
   * 显示图片，使用缓存
   * 不使用注入方式的写法，推荐使用注入写法
   *
   * @param errorImg 错误和占位符图片
   * @param url      图片地址
   * @param imgView  需要显示的图片
   */

  @SuppressLint("CheckResult")
  @Deprecated
  public static void showOldMethod(Context context, int errorImg, String url, ImageView imgView) {
    RequestOptions options = new RequestOptions();
    options.placeholder(errorImg)
        .error(errorImg)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

    Glide.with(context)
        .load(url)
        .apply(options)
        .into(imgView);

  }

  /**
   * 显示图片，使用缓存
   *
   * @param errorImg 错误和占位符图片
   * @param url      图片地址
   * @param imgView  需要显示的图片
   */

  @SuppressLint("CheckResult")
  public static void show(Context context, int errorImg, String url, ImageView imgView) {
    iAnimationAndZoomShow(context, errorImg, url).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imgView);
  }

  /**
   * 显示图片，不使用缓存
   *
   * @param errorImg 错误和占位符图片
   * @param path     图片地址(drawable、mipmap下的图片)
   * @param imgView  需要显示的图片
   */

  @SuppressLint("CheckResult")
  public static void show(Context context, int errorImg, int path, ImageView imgView) {
    iAnimationAndZoomShow(context, errorImg, path).skipMemoryCache(true).into(imgView);
  }


  /**
   * 显示渐进式动画图片--使用缓存策略
   *
   * @param errorImg 错误的资源图片
   * @param url      图片链接
   * @param imgView  组件
   */

  @SuppressLint("CheckResult")
  public static void showFade(Context context, int errorImg, String url, ImageView imgView) {
    iAnimationAndZoomShow(context, errorImg, url)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imgView);
  }

  /**
   * 圆角图片显示--使用缓存
   *
   * @param errorImg 错误的资源图片
   * @param url      图片地址
   * @param imgView  组件
   * @param dp       圆角大小
   */

  @SuppressLint("CheckResult")
  public static void showCircleRound(Context context, int errorImg, String url, ImageView imgView, int dp) {
    iAnimationAndZoomShow(context, errorImg, url)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .transform(new GlideCircleTransform(dp))
        .into(imgView);
  }

  /**
   * 圆角图片显示--不使用缓存
   *
   * @param errorImg 错误的资源图片
   * @param path     图片地址
   * @param imgView  组件
   */

  @SuppressLint("CheckResult")
  public static void showCircleRound(Context context, int errorImg, int path, ImageView imgView, int dp) {
    iAnimationAndZoomShow(context, errorImg, path)
        .transform(new GlideCircleTransform(dp))
        .into(imgView);
  }


  /**
   * 矩形圆角图片显示--使用缓存
   *
   * @param errorImg 错误的资源图片
   * @param url      图片地址
   * @param imgView  组件
   */
  @SuppressLint("CheckResult")
  public static void showRectangleCircle(Context context, int errorImg, String url, ImageView imgView) {
    iAnimationAndZoomShow(context, errorImg, url)
        .transform(new RoundedRectanglesTransformation(35, 0, RoundedRectanglesTransformation.CornerType.ALL))
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imgView);
  }

  /**
   * 矩形圆角图片显示--不使用缓存
   *
   * @param errorImg 错误的资源图片
   * @param path     图片地址
   * @param imgView  组件
   */
  @SuppressLint("CheckResult")
  public static void showRectangleCircle(Context context, int errorImg, int path, ImageView imgView) {
    iAnimationAndZoomShow(context, errorImg, path)
        .transform(new RoundedRectanglesTransformation(35, 0, RoundedRectanglesTransformation.CornerType
            .OTHER_TOP_LEFT))
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imgView);
  }


  //扩展方法
  @SuppressWarnings("ALL")
  public static GlideRequest<Drawable> iShow(Context context, int errorImg, String url) {
    return GlideApp.with(context)
        //加载URL
        .load(url)
        //占位符
        .placeholder(errorImg)
        //错误显示符
        .error(errorImg);
  }

  //扩展方法
  @SuppressWarnings("ALL")
  public static GlideRequest<Drawable> iShow(Context context, int errorImg, int path) {
    return GlideApp.with(context)
        //加载URL
        .load(path)
        //占位符
        .placeholder(errorImg)
        //错误显示符
        .error(errorImg);
  }

  //扩展方法
  @SuppressWarnings("ALL")
  public static GlideRequest<Drawable> iAnimationAndZoomShow(Context context, int errorImg, int path) {
    return iShow(context, errorImg, path)
        //动画显示方式-不显示
        .dontAnimate()
        //缩放方式--中心处缩放
        .centerCrop();
  }

  //扩展方法
  @SuppressWarnings("ALL")
  public static GlideRequest<Drawable> iAnimationAndZoomShow(Context context, int errorImg, String url) {
    return iShow(context, errorImg, url)
        //动画显示方式-不显示
        .dontAnimate()
        //缩放方式--中心处缩放
        .centerCrop();
  }
}
