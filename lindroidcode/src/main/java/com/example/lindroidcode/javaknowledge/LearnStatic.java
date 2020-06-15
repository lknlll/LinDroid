package com.example.lindroidcode.javaknowledge;

import android.util.Log;

class LearnStatic {

    private static final String TAG = LearnStatic.class.getSimpleName();
    static{
        Log.e("LearnStatic", "static initializer: ");
    }

    public static void checkTiming(){
        Log.e(TAG, "checkTiming: ");
    }

    LearnStatic(){
        Log.e(TAG, "LearnStatic Constructor: ");
    }
}
