package com.example.lindroidcode.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class DensityUtils {
    private static final String TAG = DensityUtils.class.getSimpleName();
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取当前页面的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 当前页面的高度
     * 需要传递Activity的context才能保证准确
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取手机屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     * @return float
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取手机屏幕密度（每寸像素：120/160/240/320）
     * @return int
     */
    public static int getDensityDPI(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取手机屏幕密度（和系统字体大小有关）
     * @return float
     */
    public static float getScaledDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static int[] getRealScreenWidthHeight(Context context){
        int h = 0,w = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(point);
                if (point.x < point.y) {
                    w = point.x;
                    h = point.y;
                } else {
                    w = point.y;
                    h = point.x;
                }
            }else {
                h = context.getResources().getDisplayMetrics().heightPixels;
                w = context.getResources().getDisplayMetrics().widthPixels;
            }
        }else{
            Log.e(TAG,"no windowManager");
        }
        return new int[]{w, h} ;
    }
}
