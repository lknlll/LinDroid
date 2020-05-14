package com.example.lindroidcode.customizecircleprogress;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lindroidcode.R;
import com.example.lindroidcode.utils.DensityUtils;

import java.lang.ref.WeakReference;

public class CircleProgressActivity extends AppCompatActivity {
    private RoundProgressBar mRoundProgressBar;
    private static final int MSG_PROGRESS = 3002;
    private ProgressHandler mProgressHandler;
    private static class ProgressHandler extends Handler {
        private WeakReference<CircleProgressActivity> mHost;

        ProgressHandler(CircleProgressActivity host){
            mHost = new WeakReference<>(host);
        }

        @Override
        public void handleMessage(Message msg) {
            CircleProgressActivity host = mHost.get();
            if (host != null) {
                switch (msg.what) {
                    case MSG_PROGRESS:
                        host.animCountdown(msg.arg1);
                        break;
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);
        mRoundProgressBar = findViewById(R.id.pb_countdown);
        mRoundProgressBar.setMax(100);//最大
        mRoundProgressBar.setStyle(0);//0空心，1实心
        mRoundProgressBar.setRoundColor(0x99000000);//圆环颜色
        mRoundProgressBar.setRoundWidth(DensityUtils.dp2px(4,this));//圆环宽度
        mRoundProgressBar.setRoundProgressColor(Color.WHITE);//圆环进度颜色
        mRoundProgressBar.setDisplayText(true);//是否显示中间文字
        mRoundProgressBar.setTextSize(200);//中间文字大小
        mRoundProgressBar.setTextColor(Color.WHITE);//中间文字颜色
        mProgressHandler = new ProgressHandler(this);
        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animCountdown(1);
            }
        });
    }

    private void animCountdown(int progress){
        mRoundProgressBar.setProgress(progress);
        if (100 > progress) {
            mProgressHandler.sendMessageDelayed(mProgressHandler.obtainMessage(MSG_PROGRESS,progress + 1 ,0), 29);
        }
    }
}
