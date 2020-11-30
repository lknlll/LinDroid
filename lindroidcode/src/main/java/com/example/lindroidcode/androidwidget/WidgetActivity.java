package com.example.lindroidcode.androidwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class WidgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        TextView tvSpinnerNote = findViewById(R.id.tv_spinner_note);
        tvSpinnerNote.append("\ncode in AudioRecordPlayActivity");
        tvSpinnerNote.append("\nOnItemSelectedListener 选择事件");
        tvSpinnerNote.append("\n使用基本数据类型绑定Adapter");
        tvSpinnerNote.append("\nArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);\n");
        tvSpinnerNote.append("\n使用自定义数据类型绑定BaseAdapter");
    }
}