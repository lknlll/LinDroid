package com.example.lindroidcode.constraintdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lindroidcode.databinding.ActivityConstraintBinding;

public class ConstraintActivity extends AppCompatActivity {

    private ActivityConstraintBinding mActivityConstraintBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityConstraintBinding = ActivityConstraintBinding.inflate(getLayoutInflater());
        View view = mActivityConstraintBinding.getRoot();
        setContentView(view);

        mActivityConstraintBinding.btConstraintAlpha.setOnClickListener(v -> startActivity(new Intent(ConstraintActivity.this,ConstraintAlphaActivity.class)));
        mActivityConstraintBinding.btBias.setOnClickListener(v -> startActivity(new Intent(ConstraintActivity.this,ConstraintAttributeActivity.class)));
        mActivityConstraintBinding.btConstraintGroup.setOnClickListener(v -> startActivity(new Intent(ConstraintActivity.this,ConstraintAttributeActivity.class)));

    }
}
