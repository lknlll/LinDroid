package com.example.lindroidcode.learnmultithread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lindroidcode.R;

public class MultiThreadActivity extends AppCompatActivity {

    private static final String TAG = MultiThreadActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);

        AMessage msg = new AMessage("process it");

        Waiter waiter = new Waiter(msg);
        new Thread(waiter,"waiter").start();

        Waiter waiter1 = new Waiter(msg);
        new Thread(waiter1, "waiter1").start();

        Notifier notifier = new Notifier(msg);
        new Thread(notifier, "notifier").start();
        Log.e(TAG, "onCreate: All the threads are started");
    }
}