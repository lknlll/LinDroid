package com.example.lindroidcode.camera;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;

import com.example.lindroidcode.R;

import static com.example.lindroidcode.camera.CameraEngine.CAMERA_PREVIEW_DEFAULT_HEIGHT;
import static com.example.lindroidcode.camera.CameraEngine.CAMERA_PREVIEW_DEFAULT_WIDTH;

public class TextureViewPreviewActivity extends Activity implements Camera.PreviewCallback{
    private static final String TAG = TextureViewPreviewActivity.class.getSimpleName();
    private TextureView RGBTextureView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_view_preview);

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

        RGBTextureView = findViewById(R.id.camera_preview_rgb);

        CameraEngine.getInstance().initCamera(RGBTextureView,this);

        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindowManager().getDefaultDisplay().getRealSize(point);

            if ((point.y / (float)point.x) > (CAMERA_PREVIEW_DEFAULT_WIDTH /(float)CAMERA_PREVIEW_DEFAULT_HEIGHT)) {
                Matrix matrix = new Matrix();
                matrix.setScale((point.y * CAMERA_PREVIEW_DEFAULT_HEIGHT/ (float)CAMERA_PREVIEW_DEFAULT_WIDTH ) / point.x,1);
                RGBTextureView.setTransform(matrix);//对预览进行拉伸
            }
        }
    }

    @Override
    protected void onDestroy() {
        CameraEngine.getInstance().releaseCamera();
        super.onDestroy();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}