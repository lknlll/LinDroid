package com.example.lindroidcode.camera;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;

public class FlashUtils {
    private static final String TAG = FlashUtils.class.getSimpleName();
    private Context context;
    private static boolean status = false;//记录手电筒状态

    public static boolean isStatus() {
        return status;
    }

    FlashUtils(Context context){
        this.context = context;
    }

    //打开手电筒
    public void open(Camera camera) {
        if(status){//如果已经是打开状态，不需要打开
            return;
        }

        PackageManager packageManager = context.getPackageManager();
        FeatureInfo[] features = packageManager.getSystemAvailableFeatures();
        for (FeatureInfo featureInfo : features) {
            if (PackageManager.FEATURE_CAMERA_FLASH.equals(featureInfo.name)) { // 判断设备是否支持闪光灯

                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
            }
        }
        status = true;//记录手电筒状态为打开
    }

    //关闭手电筒
    public void close(Camera camera) {
        if(!status){//如果已经是关闭状态，不需要打开
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        status = false;//记录手电筒状态为关闭
    }
}

