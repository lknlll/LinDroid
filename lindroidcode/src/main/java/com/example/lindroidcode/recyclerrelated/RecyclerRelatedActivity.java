package com.example.lindroidcode.recyclerrelated;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.lindroidcode.R;
import com.example.lindroidcode.recyclerrelated.decoration.HalfTransparentDecoration;
import com.example.lindroidcode.recyclerrelated.decoration.LeftAndRightTagDecoration;
import com.example.lindroidcode.recyclerrelated.decoration.SimpleDividerDecoration;
import com.example.lindroidcode.recyclerrelated.decoration.SimplePaddingDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Preparation: maven implementation, Adapter, ViewHolder

public class RecyclerRelatedActivity extends AppCompatActivity {
    private RecyclerView mAbcRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_related);
        mAbcRecycler = findViewById(R.id.recycler_abc);
        int decorationId = 0;
        int layoutManagerId = 0;
        if (getIntent().getExtras() != null) {
            decorationId = getIntent().getExtras().getInt("decoration");
            layoutManagerId = getIntent().getExtras().getInt("layout_manager");
        }

        NormalRecyclerAdapter normalRecyclerAdapter = new NormalRecyclerAdapter(this);
        /**
         * Layout Manager(必选)
         * Adapter(必选)
         */
        mAbcRecycler.setAdapter(normalRecyclerAdapter);
        if (0 == layoutManagerId) {
            mAbcRecycler.setLayoutManager(new LinearLayoutManager(this));
        }else if (1 == decorationId){
            mAbcRecycler.setLayoutManager(new GridLayoutManager(this,2));
        }else if (2 == decorationId){
            mAbcRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        }
        mAbcRecycler.setItemAnimator(new DefaultItemAnimator());

        if (0 == decorationId) {
            mAbcRecycler.addItemDecoration(new SimplePaddingDecoration(this));
        }else if (1 == decorationId){
            mAbcRecycler.addItemDecoration(new SimpleDividerDecoration(this));
        }
        mAbcRecycler.addItemDecoration(new LeftAndRightTagDecoration(this));//decoration 可以叠加
        mAbcRecycler.addItemDecoration(new HalfTransparentDecoration());//decoration 可以叠加

        for(int i = 0; i < 50 ; i++){
            normalRecyclerAdapter.addItemList(DataGroupPrepare.getEntryAL());
            normalRecyclerAdapter.addItemList(DataGroupPrepare.getNoticeAL());
        }
    }
}
