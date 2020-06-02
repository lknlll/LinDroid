package com.example.lindroidcode.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.lindroidcode.R;

/**
 * 换用AppcompatActivity时，切到后台再切回会出现Toolbar
 */
public class FullScreenCameraActivity extends Activity implements Camera.PreviewCallback {

    private CameraSurfaceView mCameraSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_camera);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        mCameraSurfaceView = findViewById(R.id.camera_preview);

        mCameraSurfaceView.setPreviewCallback(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCameraSurfaceView != null) {
            mCameraSurfaceView.onStart() ;
        }
    }
    @Override
    protected void onStop() {

        if (mCameraSurfaceView != null) {
            mCameraSurfaceView.onStop() ;
        }
        super.onStop();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        camera.addCallbackBuffer(data);
    }
}
