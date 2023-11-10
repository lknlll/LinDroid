package com.example.lindroidcode.xmldemo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.xmldemo.shapexml.ShapeXmlActivity;

import java.util.LinkedList;

/**
 * xml knowledge such as ViewStub
 */
public class XmlDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_demo);
        Debug.startMethodTracing();
        TextView mTvColored = findViewById(R.id.tv_colored_string);
        String worlds = getResources().getString(R.string.worlds);
        Spanned ws = Html.fromHtml(worlds);
        mTvColored.setText(ws);//TextView可以显示Html处理的文字

        TextView mTvEscaped = findViewById(R.id.tv_xml_escape);
        mTvEscaped.append("\n&#60;代表<");
        mTvEscaped.append("\n&#62;代表>");

        TextView mTvIOSCompare = findViewById(R.id.tv_ios);
        mTvIOSCompare.append("\n        drawable-ldpi=iphone@0.75x");
        mTvIOSCompare.append("\n        drawable-mdpi=iphone@1x");
        mTvIOSCompare.append("\n        drawable-hdpi=iphone@1.5x");
        mTvIOSCompare.append("\n        drawable-xhdpi=iphone@2x");
        mTvIOSCompare.append("\n        drawable-xxhdpi=iphone@3x");
        mTvIOSCompare.append("\n        drawable-xxxhdpi=iphone@4x");

        TextView mTvIncludeTag = findViewById(R.id.tv_include_tag);
        mTvIncludeTag.append("\n必须同时重载layout_width和layout_height熟悉，其他的layout属性才会起作用，否这都会被忽略掉");
        findViewById(R.id.bt_view_stub).setOnClickListener(v -> {

            startActivity(new Intent(this,ViewStubSampleActivity.class));

        });
        findViewById(R.id.bt_shape).setOnClickListener(v -> {

            startActivity(new Intent(this, ShapeXmlActivity.class));

        });
        findViewById(R.id.bt_layer_list).setOnClickListener(v -> {
            startActivity(new Intent(this, LayerListXmlActivity.class));

        });
    }
}
