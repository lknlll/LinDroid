package com.example.lindroidcode.sensors.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.Log;

import com.example.lindroidcode.sensors.OnShakeListener;

/**
 * 摇一摇产生振动
 *
 * 监听手机加速度的变化，当达到一个值的时候就触发摇一摇
 */
public class ShakeUtils implements SensorEventListener {

    private static final String TAG = ShakeUtils.class.getName();
    // 两次检测的时间间隔
    private static final int UPTATE_INTERVAL_TIME = 100;
    // 加速度变化阈值，当摇晃速度达到这值后产生作用
    private static final int SPEED_THRESHOLD = 200;

    private Context mContext;
    private OnShakeListener mShakeListener;
    private SensorManager mSensorManager = null;
    private Vibrator mVibrator = null;

    private long lastUpdateTime;
    private float lastX;
    private float lastY;
    private float lastZ;

    public ShakeUtils(Context context, OnShakeListener onShakeListener) {
        mContext = context;
        mShakeListener = onShakeListener;
        mSensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * 传感器的数值发生变化
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        Log.e(TAG, "onSensorChanged: " + event.timestamp);
        long currentUpdateTime = System.currentTimeMillis();

        long timeInterval = currentUpdateTime - lastUpdateTime;

        if (timeInterval < UPTATE_INTERVAL_TIME) {
            return;
        }

        lastUpdateTime = currentUpdateTime;
        float[] values = event.values;

        // 获得x,y,z加速度
        float x = values[0];
        float y = values[1];
        float z = values[2];

        // 获得x,y,z加速度的变化值
        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;

        // 将现在的坐标变成last坐标
        lastX = x;
        lastY = y;
        lastZ = z;

        double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ)
                / timeInterval * 10000;
        Log.e(TAG, "onSensorChanged: speed " + speed);
        if (speed > SPEED_THRESHOLD) {
            mShakeListener.shakeInfo(speed);
            //在这里可以提供一个回调
            mVibrator.vibrate(300);
        }
    }

    /**
     * 传感器的精度发生变化
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void registerSensor() {

        Sensor sensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        /**
         * 加速度传感器：Sensor.TYPE_ACCELEROMETER
         * 以m/s2测量它设备所有三个物理轴线方向（x,y,和z）加速度。
         *
         * 周围温度传感器：Sensor.TYPE_AMBIENT_TEMPERATURE
         * 检测周围空气温度。
         *
         * 重力传感器：Sensor.TYPE_GRAVITY
         * 测量重力
         *
         * 陀螺仪传感器：Sensor.TYPE_GYROSCOPE
         * 以rad/s测量设备三个物理轴线方向（x,y,和z)。旋转速度。
         *
         * 光照传感器：Sensor.TYPE_LIGHT
         * 以lx测量周围的光线级别。
         *
         * 线性加速度传感器：Sensor.TYPE_LINEAR_ACCELERATION
         * 检测沿着一个轴向的加速度。
         *
         * 磁力传感器：Sensor.TYPE_MAGNETIC_FIELD
         * 测量周围的三个物理轴线方向的磁场。
         *
         * 方向传感器： Sensor.TYPE_ORIENTATION
         * 测量设备所有三个物理轴线方向（x,y和x）的旋转角度。
         *
         * 压力传感器：Sensor.TYPE_PRESSURE
         * 测量周围空气气压
         *
         * 接近传感器：Sensor.TYPE_PROXIMITY
         * 检测物体与手机的距离
         *
         * 相对湿度传感器：Sensor.TYPE_RELATIVE_HUMIDITY
         * 检测周围空气相对湿度
         *
         * 旋转矢量传感器：Sensor.TYPE_ROTATION_VECTOR
         * 用于检测运动和检测旋转。
         *
         * 温度传感器： Sensor.TYPE_TEMPERATURE
         * 检测设备的温度
         *
         * 传感器的可用性不但在不同硬件之间有变化，而且不同的Android版本之间也可能有变化。这是因为Android传感器的引入需要有几个平台Release的过程
         */

        if (null != sensor)
            mSensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unRegisterSensor() {

        lastX = 0;
        lastY = 0;
        lastZ = 0;
        mSensorManager.unregisterListener(this);
        mShakeListener = null;
    }
}
