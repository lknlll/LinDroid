package com.example.lindroidcode.view.scroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class ScrollKnowledgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_knowledge);

        TextView tvContent = findViewById(R.id.setting_content);

        tvContent.append("this is an activity need to scroll");
        for (int i = 0 ; i < 10000 ;i ++ ){
            tvContent.append(i + " ");
        }
        tvContent.append("this is an activity need to scroll");
    }
}