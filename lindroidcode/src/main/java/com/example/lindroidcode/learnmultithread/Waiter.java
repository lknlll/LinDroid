package com.example.lindroidcode.learnmultithread;

import android.util.Log;

public class Waiter implements Runnable {

    private static final String TAG = Waiter.class.getSimpleName();
    private AMessage msg;

    public Waiter(AMessage m){
        this.msg=m;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();

        synchronized (msg) {

            Log.e(TAG, "run: " + name +" waiting to get notified at time:"+System.currentTimeMillis());
            try {
                msg.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "run: " + name +" waiter thread got notified at time:"+System.currentTimeMillis() );
            //process the message now
            Log.e(TAG,"run: " + name+" processed: "+msg.getMsg());
        }
    }
}
