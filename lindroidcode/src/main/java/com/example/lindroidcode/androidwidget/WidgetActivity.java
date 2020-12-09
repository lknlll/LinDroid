package com.example.lindroidcode.androidwidget;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lindroidcode.R;
import com.example.lindroidcode.androidwidget.spinner.EnableSameSelectionSpinner;

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

        EnableSameSelectionSpinner mSpinnerAudioRecords = findViewById(R.id.spinner_enable_same_selection);
        String[] children = new String[]{"苟利国家生死以","岂因祸福避趋之","本是同根生","相煎何太急"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, children);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);mSpinnerAudioRecords.setAdapter(adapter);
        mSpinnerAudioRecords.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                AlertDialog.Builder selectionAlert = new AlertDialog.Builder(WidgetActivity.this);
                selectionAlert.setMessage("select: " + (String)parent.getItemAtPosition(pos));
                selectionAlert.show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }
}