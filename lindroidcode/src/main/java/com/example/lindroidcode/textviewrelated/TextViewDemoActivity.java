package com.example.lindroidcode.textviewrelated;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class TextViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_demo);
        TextView tvPaddingNotWork = findViewById(R.id.tv_padding_not_work);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"BendyStraw.ttf"); // create a typeface from the assets ttf
        tvPaddingNotWork.setTypeface(typeface); // apply the typeface to the textview
    }
}
