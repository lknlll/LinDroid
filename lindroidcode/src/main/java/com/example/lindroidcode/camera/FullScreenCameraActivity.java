package com.example.lindroidcode.camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.lindroidcode.R;

import java.io.ByteArrayOutputStream;

import static com.example.lindroidcode.camera.CameraSurfaceView.CODE_BITMAP_FACTORY;

/**
 * 换用AppcompatActivity时，切到后台再切回会出现Toolbar
 */
public class FullScreenCameraActivity extends Activity implements Camera.PreviewCallback, View.OnClickListener {

    private CameraSurfaceView mCameraSurfaceView;
    private ToggleButton mToggleBitmapApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Todo permission
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
        mToggleBitmapApi = findViewById(R.id.toggle_is_system_api);

        mCameraSurfaceView.setPreviewCallback(this);
        findViewById(R.id.bt_bitmap_factory_api).setOnClickListener(this);

        ToggleButton toggleLight = findViewById(R.id.toggle_flash);
        toggleLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCameraSurfaceView.converse();
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_bitmap_factory_api:
                if (mToggleBitmapApi.isChecked()) {
                    mCameraSurfaceView.cameraTakePic(CODE_BITMAP_FACTORY);
                }else {
                    mCameraSurfaceView.cameraTakePic(1);
                }
                break;
        }
    }
}
