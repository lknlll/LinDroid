package com.example.lindroidcode.canvas;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lindroidcode.R;

/**
 * Canvas 的api 大致归类：
 *
 * 第一是以drawXXX为主的绘制方法；
 *
 * 第二是以clipXXX为主的裁剪方法；
 *
 * 第三是以scale、skew、translate和rotate组成的Canvas变换方法；
 *
 * 第四是以saveXXX和restoreXXX构成的画布锁定和还原；
 *
 * 其他。
 *
 * 需要注意一些方法不能硬件加速
 */
public class CanvasActivity extends AppCompatActivity {

    private CanvasView mCanvasView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        mCanvasView = findViewById(R.id.canvas_view);
        mCanvasView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
    }

    private void showListDialog() {
        final String[] items = { "clipRect(…)","intersect","union","clipPath 剪裁不规则图形画布", "save, restore", "saveLayer",
                "restoreToCount", "drawText", "drawTextAlignCenter", "drawTextAlignRight", "demoXfermode" };
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(CanvasActivity.this);
        listDialog.setTitle("Demo List");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                mCanvasView.setBizNo(which);
                CanvasActivity.this.setTitle(items[which]);//设置AppCompatActivity 标题
            }
        });
        listDialog.show();
    }
}
