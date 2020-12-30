package com.lin.glide.config;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.security.MessageDigest;

/**
 * @author ：王文彬 on 2020-07-13 11：26
 * @describe ：圆角处理
 * @email ：wangwenbin29@jd.com
 */
public class GlideCircleTransform extends CenterCrop {

  private float radius = 0f;

  public GlideCircleTransform() {
    this(4);
  }

  public GlideCircleTransform(int dp) {
    super();
    this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
  }

  @Override
  protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
    Bitmap transform = super.transform(pool, toTransform, outWidth, outHeight);
    return roundCrop(pool, transform);
  }

  private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
    if (source == null) {
      return null;
    }

    Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    if (result == null) {
      result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(result);
    Paint paint = new Paint();
    paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
    paint.setAntiAlias(true);
    RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
    canvas.drawRoundRect(rectF, radius, radius, paint);
    return result;
  }


  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

  }

}
