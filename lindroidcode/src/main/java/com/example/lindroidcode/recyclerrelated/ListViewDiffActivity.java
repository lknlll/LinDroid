package com.example.lindroidcode.recyclerrelated;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.lindroidcode.R;

public class ListViewDiffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_diff);
        NormalListViewAdapter normalListViewAdapter = new NormalListViewAdapter(this,0,DataGroupPrepare.getNoticeAL());
        ListView listView = findViewById(R.id.lv);
        listView.setAdapter(normalListViewAdapter);
        for(int i = 0; i < 50 ; i++){
            normalListViewAdapter.addAll(DataGroupPrepare.getEntryAL());
            normalListViewAdapter.addAll(DataGroupPrepare.getNoticeAL());
        }
    }
}