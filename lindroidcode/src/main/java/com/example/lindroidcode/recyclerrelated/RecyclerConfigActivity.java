package com.example.lindroidcode.recyclerrelated;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class RecyclerConfigActivity extends AppCompatActivity {

    private RadioGroup mRgFilter;
    private Bundle mBundleConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_config);

        TextView tvNotes = findViewById(R.id.tv_notes);
        tvNotes.setText("lib_base_recycler_helper");
        tvNotes.append("\nAccording to https://github.com/CymChad/BaseRecyclerViewAdapterHelper");
        tvNotes.append("\nAudioManagerActivity: swipe delete, item dragging");
        tvNotes.append("\nDrag issue: 嵌套调用在RecyclerView内时，重复setAdapter会导致Drag失效，");
        tvNotes.append("\n将OnItemDragListener作为implement了DraggableModule的Adapter的成员变量");
        tvNotes.append("\n外层RecyclerView bind时如果Adapter已存在则 不再 set，复用已有实例");
        tvNotes.append("\nuse DataBinding with RecyclerView for data update");
        mBundleConfig = new Bundle();

        mRgFilter = findViewById(R.id.rg_decoration);
        RadioGroup rgLayoutManager = findViewById(R.id.rg_layout_manager);

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

        rgLayoutManager.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_linear) {
                    mBundleConfig.putInt("layout_manager", 0);
                }else if (checkedId == R.id.rb_grid){
                    mBundleConfig.putInt("layout_manager", 1);
                }else if (checkedId == R.id.rb_staggered_grid){
                    mBundleConfig.putInt("layout_manager", 2);
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
        findViewById(R.id.bt_to_list_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerConfigActivity.this,ListViewDiffActivity.class);
                intent.putExtras(mBundleConfig);
                startActivity(intent);
            }
        });
        findViewById(R.id.bt_to_choose_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerConfigActivity.this,RecyclerFindAddressActivity.class);
                intent.putExtras(mBundleConfig);
                startActivity(intent);
            }
        });
    }
}
