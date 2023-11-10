package com.example.lindroidcode.recyclerrelated;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.utils.DensityUtils;
import com.google.android.material.appbar.AppBarLayout;

public class RecyclerFindAddressActivity extends AppCompatActivity {
    private RecyclerView mAbcRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        setContentView(R.layout.activity_recycler_find_address);

        AppBarLayout appBarLayout = findViewById(R.id.find_address_bar);
        appBarLayout.setPadding(0, getStatusBarHeight(), 0, 0);

        //点击左边返回按钮监听事件
        findViewById(R.id.iv_left_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAbcRecycler = findViewById(R.id.recycler_global_hot_address);

        NormalHotAddressRecyclerAdapter normalRecyclerAdapter = new NormalHotAddressRecyclerAdapter(this);
        /**
         * Layout Manager(必选)
         * Adapter(必选)
         */
        mAbcRecycler.setAdapter(normalRecyclerAdapter);
        mAbcRecycler.setLayoutManager(new LinearLayoutManager(this));

        AutoWrapView autoWrapView = findViewById(R.id.auto_wrap);
        autoWrapView.setHorizontalSpacing(DensityUtils.dp2px(8,this));
        autoWrapView.setVerticalSpacing(DensityUtils.dp2px(8,this));
        autoWrapView.setMaxLine(2);
        for(int i = 0; i < 50 ; i++){
            normalRecyclerAdapter.addItemList(DataGroupPrepare.getAddressAL());
            TextView textView = new TextView(this);
            textView.setText("武林商圈");
            textView.setTextColor(Color.parseColor("#191919"));
            textView.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_rect_c));
            textView.setPadding(DensityUtils.dp2px(10,this),DensityUtils.dp2px(8,this),
                    DensityUtils.dp2px(10,this),DensityUtils.dp2px(8,this));
            autoWrapView.addView(textView);

        }
    }
    
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
