package com.example.lindroidcode.deviceinfo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lindroidcode.R;

public class DeviceInfoActivity extends AppCompatActivity {

    private static final String TAG = DeviceInfoActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        Log.e(TAG, "onCreate, is 360 phone: " + Rom.is360());
        Log.e(TAG, "onCreate, Manufacturer: " + Build.MANUFACTURER);
    }
}