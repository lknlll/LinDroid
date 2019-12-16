package com.example.lindroidcode.leastsquares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Y轴值为-200到200 折线图
 */
public class LineChartView extends View {
    private float XPoint;                  //定义原点
    private float YPoint;
    private int XScale;                  //间隔
    private double[][] mDataSets;
    private double[][] mDeTrendSets;
    public LineChartView(Context context, double[][] dataSets, double[][] deTrendSets) {
        super(context);
        this.mDataSets = dataSets;
        this.mDeTrendSets = deTrendSets;
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        XPoint = getWidth() / 10;                       //定义原点，View宽的十分之一处
        YPoint = getHeight() / 2;
        XScale = (getWidth()*4/5) / (mDataSets[0].length);

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.holo_red_dark));
        paint.setTextSize(30);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(false);

        //绘制View边界
        canvas.drawLine(0, 0, getWidth(), 0, paint);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
        canvas.drawLine(0, 0, 0, getHeight(), paint);

        canvas.drawLine(XPoint, 0, XPoint, getHeight(), paint);//Y轴
        canvas.drawLine(XPoint, 0, XPoint + 20, 20, paint);               // 绘制箭头
        canvas.drawLine(XPoint, 0, XPoint - 20, 20, paint);
        canvas.drawLine(XPoint, YPoint, XPoint +((float)getWidth()*4/5), YPoint, paint);

        for (int i = 1; i < 10; i++) {
            canvas.drawLine(XPoint, getHeight() * i /20 , XPoint + 15,getHeight() * i /20, paint);// Y刻度
            canvas.drawText((100 - i * 10) + "", XPoint - 50,getHeight() * i /20, paint);// 文字
        }
        drawLineChartAccordToArray(mDataSets,canvas,paint);
        paint.setColor(getResources().getColor(android.R.color.holo_green_dark));
        drawLineChartAccordToArray(mDeTrendSets,canvas,paint);

    }

    private void drawLineChartAccordToArray(double[][] array, Canvas canvas,Paint paint){

        for (int j = 0; j < array[0].length; j++) {// 绘制折线

            if (array[0].length -1 != j) {

                float y0 = (float) array[1][j];
                float y1 = (float) array[1][j + 1];
                Log.i("y0", y0 + "");
                Log.i("y1", y1 + "");
                canvas.drawLine(XPoint + j * XScale,
                        (100 - y0) * ((float)getHeight() / 200),
                        XPoint + (j + 1) * XScale,
                        (100 - y1) * ((float)getHeight() / 200), paint);
            }

        }
    }

}