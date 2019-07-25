package com.example.rectview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class RectView extends View {
    //声明Paint对象
    private Paint mPaint = null;
    private int StrokeWidth = 2;
    private Rect rect = new Rect(0,0,0,0);//手动绘制矩形

    private Rect rectB = new Rect(0,0,0,0);

    private int rectNo = 0;

    public RectView(Context context){
        super(context);
        //构建对象
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置无锯齿
        mPaint.setAntiAlias(true);
        canvas.drawARGB(50,255,227,0);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(StrokeWidth);
        mPaint.setAlpha(100);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rect,mPaint);
        canvas.drawRect(rectB,mPaint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                if (0 == rectNo) {
                    motionDownHandleRect(rect,x,y);
                }else if(1 == rectNo){
                    motionDownHandleRect(rectB,x,y);
                }

            case MotionEvent.ACTION_MOVE:

                if (0 == rectNo) {
                    motionMoveHandleRect(rect,x,y);
                }else if(1 == rectNo){
                    motionMoveHandleRect(rectB,x,y);
                }
                break;

            case MotionEvent.ACTION_UP:

                rectNo++;
                if (2 == rectNo) {
                    rectNo = 0;
                }

                break;
            default:
                break;
        }
        return  true;//处理了触摸信息，消息不再传递
    }

    private void motionDownHandleRect(Rect rect,int x, int y){

        rect.right+=StrokeWidth;
        rect.bottom+=StrokeWidth;
        invalidate(rect);
        rect.left = x;
        rect.top = y;
        rect.right =rect.left;
        rect.bottom = rect.top;
    }

    private void motionMoveHandleRect(Rect rect,int x, int y){

        rect.right = x;
        rect.bottom = y;
        invalidate();
    }
}
