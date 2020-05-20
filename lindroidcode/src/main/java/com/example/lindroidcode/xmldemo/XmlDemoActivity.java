package com.example.lindroidcode.xmldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class XmlDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_demo);
        TextView mTvColored = findViewById(R.id.tv_colored_string);
        String worlds = getResources().getString(R.string.worlds);
        Spanned ws = Html.fromHtml(worlds);
        mTvColored.setText(ws);//TextView可以显示Html处理的文字
    }
}
