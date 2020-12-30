package com.example.lindroidcode.constraintdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lindroidcode.databinding.ActivityConstraintAlphaBinding;

public class ConstraintAlphaActivity extends AppCompatActivity {
    private TextView mTvGuideline;
    private ActivityConstraintAlphaBinding mActivityConstraintAlphaBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityConstraintAlphaBinding = ActivityConstraintAlphaBinding.inflate(getLayoutInflater());
        View view = mActivityConstraintAlphaBinding.getRoot();
        setContentView(view);

        mTvGuideline = mActivityConstraintAlphaBinding.tvGuideline;
        mTvGuideline.append("\napp:layout_constraintGuide_begin=\"XXX\"");
        mTvGuideline.append("\n设置Guideline的起始位置，如果android:orientation=\"vertical\"，那么是距离屏幕左边的位置，右边的位置不要设置，会自动计算。如果android:orientation=\"horizontal\"，那么设置的是距离屏幕上边的位置，下边的位置也不需要设置");
        mTvGuideline.append("\napp:layout_constraintGuide_end=\"XXX\"");
        mTvGuideline.append("\n同理，设置Guideline的结束位置");
        mTvGuideline.append("\napp:layout_constraintGuide_percent=\"XXX\"");
        mTvGuideline.append("\n数值范围是[0, 1]");
    }
}