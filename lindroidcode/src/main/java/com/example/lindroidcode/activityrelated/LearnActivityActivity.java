package com.example.lindroidcode.activityrelated;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.activityrelated.lifecycle.AActivity;

public class LearnActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_activity);

        findViewById(R.id.bt_start_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnActivityActivity.this, AActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.bt_lifecycle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnActivityActivity.this, LifecycleOverviewActivity.class);
                startActivity(intent);
            }
        });

        TextView tvOnStopTiming = findViewById(R.id.tv_on_stop_timing);
        tvOnStopTiming.append("\nCalled when you are no longer visible to the user.对用户完全不可见时调用，比如home键，锁屏");
        tvOnStopTiming.append("\n开启另一个Activity并不一定会调用onStop方法，当设置Activity的主题windowIsTranslucent属性为true是，窗口为半透明，虽然效果和直接开启一个Activity没有什么区别，但是当前Activity并不会调用onStop方法，只会调用onPause方法，当前Activity进入背景");
    }
}
