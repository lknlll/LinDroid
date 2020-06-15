package com.example.lindroidcode.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.lindroidcode.utils.DensityUtils;
import com.example.lindroidcode.utils.NV21ToBitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.ErrorCallback {

    public static final int CODE_BITMAP_FACTORY = 0;
    private static final String TAG = CameraSurfaceView.class.getSimpleName();
    private FlashUtils mFlashUtils;
    private int mWidth = -1;    //surface的宽度
    private int mHeight = -1;   //surface的高度
    private boolean mStarted;
    private Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private Camera camera;
    private Camera.PreviewCallback mPreviewCallback ;
    public void setPreviewCallback(Camera.PreviewCallback previewCallback) {
        mPreviewCallback = previewCallback ;
    }
    public CameraSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (camera != null) {
            return ;
        }
        camera = Camera.open(); // attempt to get a Camera instance
        if (camera != null) {
            //错误回调，
            camera.setErrorCallback(this);
            try {
                //设置预览用的Surface，入参的SurfaceHolder中必须包含一个Surface，所以要在surfaceChanged回调触发后才能调用；
                camera.setPreviewDisplay(holder);

                Camera.Parameters parameters = camera.getParameters();
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //摄像头默认捕获的画面byte[]是横向，竖屏拍照时，需要设置旋转90度，否者看到的相机预览方向和界面方向不相同
                    camera.setDisplayOrientation(90);
                    //Todo
                    parameters.setRotation(90);
                } else {
                    camera.setDisplayOrientation(0);
                    parameters.setRotation(0);
                }
                parameters.setPreviewFormat(ImageFormat.NV21);//默认NV21

                //设置预览框
                Camera.Size viewSizePic = getCloselyPreSize(
                        DensityUtils.getRealScreenWidthHeight(mContext)[1],
                        DensityUtils.getRealScreenWidthHeight(mContext)[0],
                        parameters.getSupportedPreviewSizes());

                if (viewSizePic != null) {
                    parameters.setPreviewSize(viewSizePic.width,
                            viewSizePic.height);
                } else {
                    Log.e(TAG, "surfaceChanged: fail to get closely size.");
                }

                camera.setParameters(parameters);
                camera.startPreview();
                //首次对焦
                try {
                    camera.autoFocus(null);
                } catch (Exception e) {
                    Log.e(TAG, "takePhoto " + e);
                }

                if (mPreviewCallback != null) {
                    //Installs a callback to be invoked for every preview frame, using buffers supplied with addCallbackBuffer(byte[]), in addition to displaying them on the screen
                    //每帧预览时触发回调，用addCallbackBuffer(byte[])提供的buffers，在回调触发时循环调用这个空间就可以循环使同一块内存
                    camera.setPreviewCallbackWithBuffer(mPreviewCallback) ;
                    Camera.Size size = parameters.getPreviewSize();
                    //todo 这里可能会oom
                    byte[] buffer = new byte[((size.width * size.height) * ImageFormat.getBitsPerPixel(ImageFormat.NV21)) / 8];

                    camera.addCallbackBuffer(buffer) ;

                    //重新计算surfaceview的尺寸
                    float widthScale = (float)size.height / DensityUtils.getRealScreenWidthHeight(mContext)[0] ;
                    float heightScale = (float)size.width / DensityUtils.getRealScreenWidthHeight(mContext)[1] ;
                    float measurWidth , measurHeight ;
                    if (widthScale > heightScale) {//宽度为基准
                        measurHeight = size.width / heightScale ;
                        measurWidth = size.height / heightScale ;
                    } else {//高度为基准
                        measurWidth = size.height / widthScale ;
                        measurHeight = size.width / widthScale ;
                    }

                    resize((int)measurWidth , (int)measurHeight);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Log.e(TAG, "surfaceChanged: open camera failed, null camera");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        holder.removeCallback(this);
        //回收释放资源
        release();
    }

    private void init(Context context){
        mStarted = false ;
        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mFlashUtils = new FlashUtils(context.getApplicationContext());
    }

    @Override
    public void onError(int error, Camera camera) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (-1 == mWidth || -1 == mHeight) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            setMeasuredDimension(mWidth, mHeight);
        }
    }

    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @param screenW  需要被进行对比的原宽
     * @param screenH 需要被进行对比的原高
     * @param preSizeList   需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    private Camera.Size getCloselyPreSize(int screenW, int screenH,
                                            List<Camera.Size> preSizeList) {

        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for (Camera.Size size : preSizeList) {
            if ((size.width == screenW) && (size.height == screenH)) {
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) screenH) / screenH;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        int count = 0;
        //设置最小分辨率为640 * 480
        for (Camera.Size size : preSizeList) {
            if (640 * 480 <= size.width * size.height) {
                count ++;
                curRatio = ((float) size.width) / size.height;
                deltaRatio = Math.abs(reqRatio - curRatio);
                if (deltaRatio < deltaRatioMin) {
                    deltaRatioMin = deltaRatio;
                    retSize = size;
                }
            }
        }
        //手机不支持640*480以上分辨率时才在小的分辨率里找
        if (0 == count) {
            for (Camera.Size size : preSizeList) {
                curRatio = ((float) size.width) / size.height;
                deltaRatio = Math.abs(reqRatio - curRatio);
                if (deltaRatio < deltaRatioMin) {
                    deltaRatioMin = deltaRatio;
                    retSize = size;
                }
            }
        }
        return retSize;
    }

    public void resize(int width, int height) {
        mWidth = width;
        mHeight = height;
        getHolder().setFixedSize(width, height);
        requestLayout();
        invalidate();
    }

    public void onStart() {
        if (mStarted) {
            return ;
        }

        if (mSurfaceHolder != null) {
            mSurfaceHolder.addCallback(this);
        }
        mStarted = true ;
    }
    public void onStop() {
        mStarted = false ;
    }

    public void cameraTakePic(final int way){
        //拍照后预览会停掉，未在预览状态时调用会触发Exception，报错：Camera2Client: takePicture: Camera 0: Already taking a picture
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Camera.Size size = camera.getParameters().getPreviewSize();
                long tS = System.currentTimeMillis();
                if (way == CODE_BITMAP_FACTORY) {

                    try{
                        YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, stream);

                        Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                        Log.e(TAG, "onPictureTaken: bitmap cost " + (System.currentTimeMillis() - tS));
                        //TODO：此处可以对位图进行处理，如显示，保存等

                        stream.close();
                    }catch(Exception ex){
                        Log.e("Sys","Error:"+ex.getMessage());
                    }
                }else {
                    Bitmap bmp = new NV21ToBitmap(mContext).nv21ToBitmap(data,size.width,size.height);
                    Log.e(TAG, "onPictureTaken: bitmap cost " + (System.currentTimeMillis() - tS));
                }
            }
        });
    }
    /**
     * 释放资源
     */
    private void release() {
        try {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.setPreviewCallbackWithBuffer(null);
                camera.setErrorCallback(null);
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void openFlashLight(){
        mFlashUtils.open(camera);
    }

    public void closeFlashLight(){
        mFlashUtils.close(camera);
    }

    //改变手电筒状态
    public void converse(){
        if(FlashUtils.isStatus()){
            mFlashUtils.close(camera);
        }else{
            mFlashUtils.open(camera);
        }
    }
    public boolean getFlashlightStatus(){
        return FlashUtils.isStatus();
    }
}
