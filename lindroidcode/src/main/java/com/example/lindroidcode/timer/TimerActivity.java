package com.example.lindroidcode.timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lindroidcode.R;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.lindroidcode.utils.DataTimeUtils.getHour;

public class TimerActivity extends AppCompatActivity {

    private static final String TAG = TimerActivity.class.getSimpleName();
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Log.e(TAG, "run: " + getHour(new Date()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer.schedule(task,0,60000);
    }

    @Override
    protected void onStop() {
        timer.cancel();
        super.onStop();
    }
}