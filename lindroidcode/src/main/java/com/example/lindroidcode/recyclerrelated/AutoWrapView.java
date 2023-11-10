package com.example.lindroidcode.recyclerrelated;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class AutoWrapView extends ViewGroup {
    private int verticalSpacing = 10;
    private int horizontalSpacing = 5;  //childView水平间距
    private int maxLine = 1;

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    public AutoWrapView(Context context) {
        super(context);
    }

    public AutoWrapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoWrapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);  //计算childView的大小
        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);  //viewGroup的宽度
        int paddingLeft = getPaddingLeft();  //获取到padding值
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int childLeft = paddingLeft;  //childView的总宽度
        int lineHeight = paddingTop;  //ViewGroup的高度 还需要加paddingBottom
        int childrenCount = getChildCount();

        int lineNum = 1;
        //计算ViewGroup的高度，根据childView确定viewGroup的高度
        for (int i = 0; i < childrenCount; i++) {
            View child = getChildAt(i);
            lineHeight = Math.max(child.getMeasuredHeight() + paddingTop, lineHeight);
            //当childView的总宽度大于viewGroup的宽度时，换行！
            //高度加1(1 = childView的高度和纵向间距) ，重置childLeft
            if (child.getMeasuredWidth() + childLeft + paddingRight > layoutWidth) {
                lineNum++;

                //超过行数限制
                if (lineNum > maxLine) {
                    setMeasuredDimension(layoutWidth, lineHeight + paddingBottom);  //设置viewGroup的大小
                    return;
                }
                childLeft = paddingLeft;
                lineHeight += child.getMeasuredHeight() + verticalSpacing;
            }
            childLeft += child.getMeasuredWidth() + horizontalSpacing;
        }
        setMeasuredDimension(layoutWidth, lineHeight + paddingBottom);  //设置viewGroup的大小
    }


    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //这里是确定childView的位置，计算的原理和onMeasure差不多
        int layoutWidth = r - l;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int childLeft = paddingLeft;
        int lineHeight = paddingTop;
        int childrenCount = getChildCount();

        int lineNum = 1;

        for (int i = 0; i < childrenCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            if (child.getMeasuredWidth() + childLeft + paddingRight > layoutWidth) {
                lineNum++;

                //超过行数限制
                if (lineNum > maxLine) {
                    child.setVisibility(View.GONE);
                    continue;
                }

                childLeft = paddingLeft;
                lineHeight += child.getMeasuredHeight() + verticalSpacing;


            }
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            //这里是设置childView的位置
            child.layout(childLeft, lineHeight, childLeft + child.getMeasuredWidth(), lineHeight + child.getMeasuredHeight());
            childLeft += child.getMeasuredWidth() + horizontalSpacing;
        }
    }
}

