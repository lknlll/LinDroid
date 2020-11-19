package com.example.lindroidcode.nativerelated;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class NativeRelatedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_related);
        LinNativeUtils.initJniImpl();
        int[][] arr = LinNativeUtils.fetchTwoDimensionalArrayImpl(2);

        TextView tvTwoDimensionArr = findViewById(R.id.tv_jni_fetch_result);
        tvTwoDimensionArr.append("\n二维数组jni中对应jobjectArray，二维int数组，签名是[[I");
        for (int i = 0; i < arr.length; i++) {
            for (int i1 = 0; i1 < arr[i].length; i1++) {
                tvTwoDimensionArr.append("\narr[" + i + "]" + "[" + i1 + "] = " + arr[i][i1]);
            }
        }
    }
}