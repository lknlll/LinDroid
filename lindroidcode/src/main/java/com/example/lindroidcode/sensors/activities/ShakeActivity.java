package com.example.lindroidcode.sensors.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.sensors.OnShakeListener;
import com.example.lindroidcode.sensors.utils.ShakeUtils;

public class ShakeActivity extends AppCompatActivity implements OnShakeListener {

    ShakeUtils mShakeUtils;
    TextView mTextShakeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        mTextShakeInfo = findViewById(R.id.text_shake_info);
        mTextShakeInfo.setMovementMethod(new ScrollingMovementMethod());
        mShakeUtils = new ShakeUtils(this,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShakeUtils.registerSensor();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mShakeUtils.unRegisterSensor();
    }

    @Override
    public void shakeInfo(double speed) {
        String sOrigin = mTextShakeInfo.getText().toString();
        mTextShakeInfo.setText(sOrigin + "\nspeed: " + speed);
    }
}
