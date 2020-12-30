package com.example.lindroidcode.guava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lindroidcode.databinding.ActivityGuavaOverAllBinding;

public class GuavaOverAllActivity extends AppCompatActivity {

    private ActivityGuavaOverAllBinding mActivityGuavaOverAllBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityGuavaOverAllBinding = ActivityGuavaOverAllBinding.inflate(getLayoutInflater());
        View view = mActivityGuavaOverAllBinding.getRoot();
        setContentView(view);
        mActivityGuavaOverAllBinding.btGuavaFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuavaOverAllActivity.this,GuavaFilterActivity.class));
            }
        });
    }
}