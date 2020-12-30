package com.example.lindroidcode.utils;

import android.widget.TextView;

public class TextOperateUtils {
    public static void appendLineStartWithChangeLine(TextView textView, String s){
        textView.append("\n");
        textView.append(s);
    }
    public static void appendLineStartWithChangeLine(TextView textView, String[] sArr){
        for (String s : sArr) {
            textView.append("\n");
            textView.append(s);
        }
    }
}
