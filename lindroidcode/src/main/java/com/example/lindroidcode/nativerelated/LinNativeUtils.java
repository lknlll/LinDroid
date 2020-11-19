package com.example.lindroidcode.nativerelated;

import static com.example.lindroidcode.nativerelated.LinNative.fetchTwoDimensionalArray;
import static com.example.lindroidcode.nativerelated.LinNative.initJni;

public class LinNativeUtils {
    static {
        try {
            System.loadLibrary("lindroid");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static int initJniImpl(){
        return initJni();
    }

    public static int[][] fetchTwoDimensionalArrayImpl(int size){
        return fetchTwoDimensionalArray(size);
    }

}
