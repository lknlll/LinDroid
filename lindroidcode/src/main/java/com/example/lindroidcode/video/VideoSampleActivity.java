package com.example.lindroidcode.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lindroidcode.R;

public class VideoSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_sample);

        findViewById(R.id.bt_metadata_retriever).setOnClickListener(v -> {
            startActivity(new Intent(this,RetrieveMetadataActivity.class));
        });
    }
}