package com.example.lindroidcode.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CanvasView extends View {

    private static final String TAG = CanvasView.class.getSimpleName();
    private int mBizNo;
    private Paint mPaint;
    private Path mPath;

    public CanvasView(Context context) {
        super(context);
        initView();
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (0 == mBizNo) {
            apiClipRectEffect(canvas);
        }else if (1 == mBizNo){
            apiIntersectEffect(canvas);
        }else if (2 == mBizNo){
            apiUnionEffect(canvas);
        }else if (3 == mBizNo){
            apiClipPathEffect(canvas);
        }else if (4 == mBizNo){
            apiSaveAndRestore(canvas);
        }else if (5 == mBizNo){
            apiSaveLayerEffect(canvas);
        }else if (6 == mBizNo){
            apiRestoreToCountEffect(canvas);
        }
    }

    private void initView(){
        // 实例化画笔并设置属性
        mPaint = new Paint();

    }
    private void apiClipRectEffect(Canvas canvas){
        canvas.drawColor(Color.BLUE);
        canvas.clipRect(0, 0, 500, 500);
        //clipRect是将当前画布裁剪为一个矩形，clipRect之后画的图像仅仅只能在画在这块矩形区域中，超出的部分则看不到
        /**
         * 除int 坐标外，还可以float坐标
         * clipRect(float left, float top, float right, float bottom)
         * 还有两个与之对应的方法，传入rect或rectf即可。
         *
         * clipRect(Rect rect)
         * clipRect(RectF rect)
         * RectF中涉及计算的时候数值类型均为float型
         *
         * canvas.clipRegion(mRegionA);//不受Canvas的变换影响，已废弃
         *
         */
        canvas.drawColor(Color.RED);
    }

    private void apiIntersectEffect(Canvas canvas){
        canvas.drawColor(Color.BLUE);
        Rect rect = new Rect(0, 0, 500, 500);
        boolean iResult = rect.intersect(250, 250, 750, 750);//使rect 变为intersect 传入的矩形和原坐标的交，返回false表示两者不相交，rect不会变化
        canvas.clipRect(rect);
        canvas.drawColor(Color.RED);
    }
    private void apiUnionEffect(Canvas canvas){
        canvas.drawColor(Color.BLUE);
        Rect rect = new Rect(0, 0, 500, 500);
        rect.union(250, 250, 750, 750);//与intersect相反，取的是相交区域最远的左上端点作为新区域的左上端点，而取最远的右下端点作为新区域的右下端点
        canvas.clipRect(rect);
        canvas.drawColor(Color.RED);
    }
    private void apiClipPathEffect(Canvas canvas){
        // 实例化路径
        mPath = new Path();
        // 移动起点至[50,50]
        mPath.moveTo(50, 50);
        mPath.lineTo(75, 23);
        mPath.lineTo(150, 100);
        mPath.lineTo(80, 110);
        // 闭合路径
        mPath.close();

        // 在原始画布上绘制蓝色
        canvas.drawColor(Color.BLUE);
        // 按照路径进行裁剪
        canvas.clipPath(mPath);
        // 在裁剪后剩余的画布上绘制红色
        canvas.drawColor(Color.RED);
    }
    private void apiSaveAndRestore(Canvas canvas){
        mPaint.setColor(Color.RED);
        canvas.drawRect(50, 50, 250, 250, mPaint);
        // 保存画布
        canvas.save();
        canvas.rotate(30);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(100, 100, 200, 200, mPaint);
        // 还原画布 还原之后 rotate 操作不影响下面的绿圆
        canvas.restore();//save()和restore()区域内的代码可以简单理解为一个图层，层是一个个封装在Canvas中的Bitmap
        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(150, 150, 40, mPaint);
    }
    private void apiSaveLayerEffect(Canvas canvas){
        mPaint.setColor(Color.RED);
        canvas.drawRect(50, 50, 250, 250, mPaint);
        // 保存画布
        int layerId = canvas.saveLayer(0, 0, 250, 250, null, Canvas.ALL_SAVE_FLAG);//保存一个区域的画布 Android P 以后只有ALL_SAVE_FLAG被接收，别的flag会被忽略
        // 保存画布并设置透明度
        /*canvas.saveLayerAlpha(0, 0, 250, 250, 100, Canvas.ALL_SAVE_FLAG);*/
        Log.e(TAG, "apiSaveLayerEffect: " + layerId);
        canvas.rotate(30);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(100, 100, 200, 200, mPaint);
        // 还原画布 还原之后 rotate 操作不影响下面的绿圆
        canvas.restore();//save()和restore()区域内的代码可以简单理解为一个图层，层是一个个封装在Canvas中的Bitmap
        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(150, 150, 40, mPaint);
    }
    private void apiRestoreToCountEffect(Canvas canvas){
        // 保存画布，并且得到id01
        int saveID1 = canvas.save();
        // 旋转画布
        canvas.rotate(30);
        // 画红色的矩形
        mPaint.setColor(Color.RED);
        canvas.drawRect(50, 50, 250, 250, mPaint);
        // 建立新的层，得到id02
        int saveID2 = canvas.save();
        // 画蓝色的矩形
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(100, 100, 200, 200, mPaint);
        // 还原画布01
        canvas.restoreToCount(saveID1);
        //查看save了几个
        canvas.getSaveCount();
        // 画绿色圆形
        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(150, 150, 40, mPaint);
    }
    public void setBizNo(int bizNo) {
        mBizNo = bizNo;
        postInvalidate();
    }
}
