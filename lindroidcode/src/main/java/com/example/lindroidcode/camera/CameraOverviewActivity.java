package com.example.lindroidcode.camera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class CameraOverviewActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_overview);
        setTitle("Camera");

        TextView tvSurfaceOverview = findViewById(R.id.tv_surface_overview);
        tvSurfaceOverview.append("\nSurfaceView有一个成员：SurfaceHolder，控制Surface的size和format");
        tvSurfaceOverview.append("\n通过getHolder获取使用，添加接口SurfaceHolder.Callback之后可以获取Surface生命周期");
        tvSurfaceOverview.append("\nSurfaceHolder.Callback有三个方法");
        tvSurfaceOverview.append("\nsurfaceCreated");
        tvSurfaceOverview.append("\nsurfaceChanged 在surfaceCreated后立即触发一次");
        tvSurfaceOverview.append("\nsurfaceDestroyed ");

        TextView tvTextureOverview = findViewById(R.id.tv_texture_overview);
        tvTextureOverview.append("\nMatrix matrix = new Matrix();");
        tvTextureOverview.append("\nmatrix.setScale((point.y * CAMERA_PREVIEW_DEFAULT_HEIGHT/ (float)CAMERA_PREVIEW_DEFAULT_WIDTH ) / point.x,1);");
        tvTextureOverview.append("\n//对预览进行拉伸");
        tvTextureOverview.append("\nRGBTextureView.setTransform(matrix);");

        findViewById(R.id.bt_fullscreen_camera).setOnClickListener(this);
        findViewById(R.id.bt_fullscreen_camera_texture).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_fullscreen_camera:
                startActivity(new Intent(CameraOverviewActivity.this,FullScreenCameraActivity.class));
                break;
            case R.id.bt_fullscreen_camera_texture:
                startActivity(new Intent(CameraOverviewActivity.this,TextureViewPreviewActivity.class));
                break;
        }
    }
}
