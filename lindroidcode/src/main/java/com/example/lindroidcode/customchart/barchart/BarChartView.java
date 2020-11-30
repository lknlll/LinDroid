package com.example.lindroidcode.customchart.barchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Y轴值为-200到200 折线图
 */
public class BarChartView extends View {
    private float XPoint;                  //定义原点
    private float YPoint;
    private float barWidth;                  //barWidth
    private float maxY = 3;                  //最大Y值
    private float minY = -3;                  //最小Y值
    private float topBottomMargin;//上下边距
    private double[][] mDataSets;
    public BarChartView(Context context, double[][] dataSets) {
        super(context);
        this.mDataSets = dataSets;
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        topBottomMargin = getHeight()/8 ;

        XPoint = getWidth() / 10;                       //定义原点，View宽的十分之一处
        YPoint = getHeight() / 2;
        barWidth = (getWidth()*9/10) / ((mDataSets[0].length - 1) * 3 + 1);

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.holo_red_dark));
        paint.setTextSize(34);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(false);

        //绘制View边界
        canvas.drawLine(0, 0, getWidth(), 0, paint);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
        canvas.drawLine(0, 0, 0, getHeight(), paint);

//        canvas.drawLine(XPoint, 0, XPoint, getHeight(), paint);//Y轴
//        canvas.drawLine(XPoint, 0, XPoint + 20, 20, paint);               // 绘制箭头右半半
//        canvas.drawLine(XPoint, 0, XPoint - 20, 20, paint);// 绘制箭头左半半
        canvas.drawLine(XPoint, YPoint, getWidth(), YPoint, paint);

        float countLines = maxY - minY;
        for (int i = 0; i <= countLines ; i++) {
            paint.setColor(0xFFD8D8D8);
            canvas.drawLine(XPoint,
                    (getHeight() - 2 * topBottomMargin) * i / countLines + topBottomMargin,
                    getWidth(),
                    (getHeight() - 2 * topBottomMargin) * i / countLines + topBottomMargin, paint);// Y刻度
            paint.setColor(0xFF8F97C3);

            canvas.drawText(((int)maxY - i) + "", XPoint - 50,(getHeight() - 2 * topBottomMargin)  * i /countLines + topBottomMargin, paint);// Y文字
        }
        drawBarChartAccordToArray(mDataSets,canvas,paint);

    }

    private void drawBarChartAccordToArray(double[][] array, Canvas canvas,Paint paint){

        for (int j = 0; j < array[0].length; j++) {// 绘制bar

            float y0 = (float) array[1][j];
            Log.i("y0", y0 + "");
            if (0 > y0) {
                paint.setColor(0xFFF7B500);
                canvas.drawRect(XPoint + j * 3 * barWidth,
                        YPoint,
                        XPoint + j * 3 * barWidth + barWidth,
                        (maxY - y0) * ((float)(getHeight() - 2 * topBottomMargin)  / (maxY - minY)) + topBottomMargin, paint);
            }else if (0 < y0){
                paint.setColor(0xFF1432CE);
                canvas.drawRect(XPoint + j * 3 * barWidth,
                        (maxY - y0) * ((float)(getHeight() - 2 * topBottomMargin)  / (maxY - minY)) + topBottomMargin,
                        XPoint + j * 3 * barWidth + barWidth,
                        YPoint, paint);
            }
            paint.setColor(0xFF8F97C3);
            canvas.drawText("RR",XPoint + j * 3 * barWidth,getHeight() - topBottomMargin + 50 ,paint);

        }
    }

}