package com.example.lindroidcode.learnmultithread;

import android.util.Log;

public class Notifier implements Runnable {

    private static final String TAG = Notifier.class.getSimpleName();
    private AMessage msg;

    public Notifier(AMessage msg) {
        this.msg = msg;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        Log.e(TAG, "run: " + name + " started");

        try {
            Thread.sleep(1000);
            synchronized (msg){
                msg.setMsg(name+" Notifier work done");
                msg.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
