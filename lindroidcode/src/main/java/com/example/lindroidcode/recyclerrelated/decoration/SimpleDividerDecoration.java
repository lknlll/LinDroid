package com.example.lindroidcode.recyclerrelated.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lindroidcode.R;

/**
 * getItemOffsets()和 onDraw()2个方法实现分割线，首先用 getItemOffsets给item下方空出一定高度的空间（例子中是1dp），然后用onDraw绘制这个空间
 */
public class SimpleDividerDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = SimpleDividerDecoration.class.getSimpleName();
    private int dividerHeight;
    private Paint dividerPaint;

    public SimpleDividerDecoration(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.colorAccent));
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }

    /**
     * 绘制到item 之下
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //不是每个item调用一次

        int childCount = parent.getChildCount();//item
        int left = parent.getPaddingLeft();//绘制分割线左侧起点
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            //除最后一个，每个子View计算一次
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }
}
