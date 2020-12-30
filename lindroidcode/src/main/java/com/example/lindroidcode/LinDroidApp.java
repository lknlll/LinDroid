package com.example.lindroidcode;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.tencent.bugly.crashreport.CrashReport;

public class LinDroidApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "1bd0279b60", false);

        MultiDex.install(this);
    }
}
