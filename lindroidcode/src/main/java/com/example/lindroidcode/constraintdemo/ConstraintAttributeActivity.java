package com.example.lindroidcode.constraintdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lindroidcode.databinding.ActivityConstraintAttributeBinding;

public class ConstraintAttributeActivity extends AppCompatActivity {

    private ActivityConstraintAttributeBinding mActivityConstraintAttributeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityConstraintAttributeBinding = ActivityConstraintAttributeBinding.inflate(getLayoutInflater());
        View view = mActivityConstraintAttributeBinding.getRoot();
        setContentView(view);

        TextView tvKnowledge = mActivityConstraintAttributeBinding.tvKnowledge;
        tvKnowledge.append("\n");
        tvKnowledge.append("\nGroup,几个控件需要同时消失或者出现");
        tvKnowledge.append("\nUI不可见");
        tvKnowledge.append("\napp:constraint_referenced_ids=\"bt_bias_demo_a,bt_bias_demo_b\"");
        tvKnowledge.append("\n设置Group引用的View");
    }
}
