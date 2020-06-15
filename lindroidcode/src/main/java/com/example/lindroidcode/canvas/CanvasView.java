package com.example.lindroidcode.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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
        }else if (7 == mBizNo){
            apiDrawTextEffect(canvas);
        }else if (8 == mBizNo){
            apiDrawTextWithAlignEffect(canvas, Paint.Align.CENTER);
        }else if (9 == mBizNo){
            apiDrawTextWithAlignEffect(canvas, Paint.Align.RIGHT);
        }else if (10 == mBizNo){
            demoXfermode(canvas);
        }
    }

    private void initView(){
        // 实例化画笔并设置属性
        mPaint = new Paint();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//在View的构造函数中关闭硬件加速可以解决setXfermode(mMode)效果不正确问题
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

    /**
     * 3 groups of drawText APIs
     * // 第一类
     * public void drawText (String text, float x, float y, Paint paint)
     * public void drawText (String text, int start, int end, float x, float y, Paint paint)
     * public void drawText (CharSequence text, int start, int end, float x, float y, Paint paint)
     * public void drawText (char[] text, int index, int count, float x, float y, Paint paint)
     *
     * // 第二类
     * public void drawPosText (String text, float[] pos, Paint paint)
     * public void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
     *
     * // 第三类
     * public void drawTextOnPath (String text, Path path, float hOffset, float vOffset, Paint paint)
     * public void drawTextOnPath (char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint)
     *
     * drawPosText ()是根据一个个坐标点指定文字位置，drawTextOnPath ()是根据路径绘制
     * @param canvas
     */
    private void apiDrawTextEffect(Canvas canvas){
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(12);
        paint.setTextSize(100);

        String text="text(200, 400)";
        canvas.drawText(text, 200, 400, paint);

        //画两条线标记位置
        paint.setStrokeWidth(4);
        paint.setColor(Color.RED);
        canvas.drawLine(0, 400, 2000, 400, paint);
        paint.setColor(Color.BLUE);
        canvas.drawLine(200, 0, 200, 2000, paint);
    }
    private void apiDrawTextWithAlignEffect(Canvas canvas,Paint.Align align){
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(12);
        paint.setTextSize(100);
        paint.setTextAlign(align);

        String text="Text(400, 400)";
        canvas.drawText(text, 400, 400, paint);
        /**
         * x与文字对齐方式有关（通过Paint.setTextAlign()指定，默认为left）
         * 左对齐 — 文字的左边界
         * 居中对齐 — 文字的中心位置
         * 右对齐 — 文字的右边界
         *
         * y对应并不是文字的下边界，而是基准线Baseline
         */

        //画两条线标记位置
        paint.setStrokeWidth(4);
        paint.setColor(Color.RED);
        canvas.drawLine(0, 400, 2000, 400, paint);
        paint.setColor(Color.BLUE);
        canvas.drawLine(400, 0, 400, 2000, paint);

        paint.setStrokeWidth(1);
        Paint.FontMetrics fontMetrics=paint.getFontMetrics();//文字所占矩形
        Log.e(TAG, "apiDrawTextWithAlignEffect: " + fontMetrics.top + fontMetrics.ascent + fontMetrics.descent + fontMetrics.bottom);
        canvas.drawLine(0, 400 + fontMetrics.top, 2000, 400 + fontMetrics.top, paint);
        paint.setColor(Color.BLACK);
        canvas.drawLine(0, 400 + fontMetrics.ascent, 2000, 400 + fontMetrics.ascent, paint);
        paint.setColor(Color.RED);
        canvas.drawLine(0, 400 + fontMetrics.descent, 2000, 400 + fontMetrics.descent, paint);
        paint.setColor(Color.GREEN);
        canvas.drawLine(0, 400 + fontMetrics.bottom, 2000, 400 + fontMetrics.bottom, paint);

        /**
         * 使文字在给出的矩形中居中展示
         */
        //矩形背景
        Paint bgRect=new Paint();
        bgRect.setStyle(Paint.Style.FILL);
        bgRect.setColor(Color.YELLOW);
        RectF rectF=new RectF(200, 800, 800, 1400);
        canvas.drawRect(rectF, bgRect);

        canvas.drawLine(0, 800+300, 2000, 800+ 300, paint);

        Paint textPaint=new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(8);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetricsB =textPaint.getFontMetrics();
        //计算baseline
        float distance=(fontMetricsB.bottom - fontMetricsB.top)/2 - fontMetricsB.bottom;
        float baseline=rectF.centerY()+distance;
        canvas.drawText(text, rectF.centerX(), baseline, textPaint);
    }
    private void demoXfermode(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        canvas.drawRect(new RectF(0, 0, 800, 1400),mPaint);
        PorterDuffXfermode mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        mPaint.setXfermode(mPorterDuffXfermode);
        mPaint.setColor(Color.GREEN);
        mPaint.setAlpha(99);
        canvas.drawRect(new RectF(0, 0, 1000, 1600),mPaint);
        mPaint.setXfermode(null);
        mPaint.setColor(0xFF508CEE);
        canvas.drawRect(new RectF(1000, 1600, 1010, 1610),mPaint);
    }
    public void setBizNo(int bizNo) {
        mBizNo = bizNo;
        postInvalidate();
    }
}
