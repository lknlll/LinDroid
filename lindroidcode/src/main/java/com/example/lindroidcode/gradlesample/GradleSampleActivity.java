package com.example.lindroidcode.gradlesample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lindroidcode.BuildConfig;
import com.example.lindroidcode.R;

public class GradleSampleActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradle_sample);

        TextView tvGradleConfig = findViewById(R.id.tv_gradle_config);

        tvGradleConfig.append(BuildConfig.BUILD_ENV);
    }
}