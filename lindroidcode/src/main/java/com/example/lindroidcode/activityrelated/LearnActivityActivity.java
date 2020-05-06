package com.example.lindroidcode.activityrelated;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lindroidcode.R;
import com.example.lindroidcode.activityrelated.lifecycle.AActivity;

public class LearnActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_activity);

        findViewById(R.id.bt_start_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnActivityActivity.this, AActivity.class);
                startActivity(intent);
            }
        });
    }
}
