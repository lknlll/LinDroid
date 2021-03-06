package com.example.lindroidcode.displaycutout;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.lindroidcode.R;

public class DisplayCutoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cutout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//此处缺少会导致切出再切回时会产生status bar
        //使内容出现在status bar后边，如果要使用全屏的话再加上View.SYSTEM_UI_FLAG_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            /**
             * 默认模式（LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT）
             * 刘海区绘制模式（ LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES）
             * 刘海区不绘制模式（LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER）
             */
        }
        getWindow().setAttributes(lp);
    }
}
