package com.example.lindroidcode.recyclerrelated.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class HalfTransparentDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = HalfTransparentDecoration.class.getSimpleName();
    private Paint mPaint;
    private int layerId;
    private LinearGradient linearGradient;
    //Xfermode有三个子类：AvoidXfermode, PixelXorXfermode和PorterDuffXfermode，前两个类在API 16被遗弃了，
    // PorterDuffXfermode类主要用于图形合成时的图像过渡模式计算，其概念来自于1984年在ACM SIGGRAPH计算机图形学出版物上发表了“Compositing digital images（合成数字图像）”的Tomas Porter和Tom Duff，
    // PorterDuffXfermode类名就来源于这俩人的名字组合PorterDuff
    //
    private Xfermode xfermode;

    public HalfTransparentDecoration(){
        mPaint = new Paint();
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);//透明度 = 源透明度 * 目标透明度；颜色 = 源透明度 * 目标颜色；
        /**
         * LinearGradient 线性渐变（x0，y0）表示渐变的起点坐标 （x1，y1）则表示渐变的终点坐标，是相对于屏幕坐标系，colors和positions都是数组，可以传入多个颜色和颜色的位置，
         * 平铺模式Shader.TileMode.CLAMP，如果着色器超出原始边界范围，会复制边缘颜色
         */
        linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, 100.0f, new int[]{0, Color.BLACK}, null, Shader.TileMode.CLAMP);

    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);

        /**
         * 设置图像的过渡模式，过渡是指图像的饱和度、颜色值等参数的计算结果的图像表现
         */
        mPaint.setXfermode(xfermode);
        mPaint.setShader(linearGradient);//着色器
        canvas.drawRect(0.0f, 0.0f, parent.getRight(), 200.0f, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);//还原画布
        Log.e(TAG, "onDrawOver: layerId = " + layerId );
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        layerId = c.saveLayer(0.0f, 0.0f, (float) parent.getWidth(), (float) parent.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);//保存画布
        Log.e(TAG, "onDraw: layerId = " + layerId );
    }
}
