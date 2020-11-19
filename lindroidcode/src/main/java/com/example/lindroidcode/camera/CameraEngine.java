package com.example.lindroidcode.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.TextureView;

import java.io.IOException;
import java.util.List;

public class CameraEngine implements TextureView.SurfaceTextureListener{
    private Camera mCamera;
    private SurfaceTexture mSurfaceTexture = null;

    public static final int CAMERA_PREVIEW_DEFAULT_WIDTH = 640;
    public static final int CAMERA_PREVIEW_DEFAULT_HEIGHT = 480;
    private static final CameraEngine INSTANCE = new CameraEngine();

    public static CameraEngine getInstance(){
        return INSTANCE;
    }

    /**
     *
     *
     * @param textureView
     * @param previewCallback
     */
    public void initCamera(TextureView textureView, Camera.PreviewCallback previewCallback){
        textureView.setSurfaceTextureListener(this);
        int cameras = Camera.getNumberOfCameras();

        if (cameras > 0) {
            Camera camera = Camera.open(0);
            camera.setDisplayOrientation(90);
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
            boolean isSupported = false;//check resolution
            for (Camera.Size size : sizes) {
                if (CAMERA_PREVIEW_DEFAULT_WIDTH == size.width && CAMERA_PREVIEW_DEFAULT_HEIGHT == size.height) {
                    isSupported = true;
                    break;
                }
            }
            if (isSupported) {
                parameters.setPreviewSize(CAMERA_PREVIEW_DEFAULT_WIDTH,CAMERA_PREVIEW_DEFAULT_HEIGHT);
                camera.setParameters(parameters);
                camera.setPreviewCallback(previewCallback);
                mCamera = camera;
                doCameraPreview();
            }else {
            }
        }else {
        }
    }

    private void doCameraPreview(){
        if (mSurfaceTexture != null && mCamera != null) {
            try {
                mCamera.setPreviewTexture(mSurfaceTexture);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //首次对焦
            try {
                mCamera.autoFocus(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //
        }
    }
    private void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        mSurfaceTexture = surfaceTexture;
        doCameraPreview();
    }

    public void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }

            mSurfaceTexture = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        setSurfaceTexture(surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        setSurfaceTexture(null);
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
