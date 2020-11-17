package com.example.lindroidcode.textviewrelated;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class TextViewDemoActivity extends AppCompatActivity {

    private TextView mTvPaddingNotWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_demo);
        mTvPaddingNotWork = findViewById(R.id.tv_padding_not_work);
        TextView tvTvApi = findViewById(R.id.tv_tv_api);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"BendyStraw.ttf"); // create a typeface from the assets ttf
        mTvPaddingNotWork.setTypeface(typeface); // apply the typeface to the textview
        mTvPaddingNotWork.append("\n 可滚动TextView：布局的xml文件中设置滚动条属性，android:scrollbars = \"vertical\",在类中为TextView setMovementMethod");
        mTvPaddingNotWork.setMovementMethod(ScrollingMovementMethod.getInstance());

        tvTvApi.append("\n android:maxEms=\"8\" not work use android:maxLength=\"8\" instead");
        tvTvApi.append("\n setTextColor(0xFF0000FF);//0xFF0000FF是int类型的数据，三段分别表示0x|FF|0000FF，0x是代表颜色整数的标记，ff是表示透明度，0000FF表示颜色，注意：必须带透明度");

        findViewById(R.id.bt_to_edit_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TextViewDemoActivity.this,EditTextDemoActivity.class));
            }
        });
    }
}
