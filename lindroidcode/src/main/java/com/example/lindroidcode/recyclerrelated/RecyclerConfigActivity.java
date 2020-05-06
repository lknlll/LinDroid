package com.example.lindroidcode.recyclerrelated;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.lindroidcode.R;

public class RecyclerConfigActivity extends AppCompatActivity {

    private RadioGroup mRgFilter;
    private Bundle mBundleConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_config);
        mBundleConfig = new Bundle();

        mRgFilter = findViewById(R.id.rg_decoration);

        mRgFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_divider_decoration) {
                    mBundleConfig.putInt("decoration", 1);
                }else if (checkedId == R.id.rb_padding_decoration){
                    mBundleConfig.putInt("decoration", 0);
                }
            }
        });
        Button btToRecycler = findViewById(R.id.bt_to_recycler);
        btToRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerConfigActivity.this,RecyclerRelatedActivity.class);
                intent.putExtras(mBundleConfig);
                startActivity(intent);
            }
        });
    }
}
