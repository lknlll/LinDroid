package com.example.lindroidcode.greendaoatoz;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.lindroidcode.R;
import com.example.lindroidcode.greendaoatoz.beans.AudioBean;
import com.example.lindroidcode.greendaoatoz.common.UserBeanDaoUtils;
import com.example.lindroidcode.recyclerrelated.quickadapter.DragAndSwipeAdapter;
import com.example.lindroidcode.utils.FileUtils;

import java.io.File;
import java.util.List;

public class AudioManagerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DragAndSwipeAdapter mAdapter;
    private UserBeanDaoUtils mUserBeanDaoUtils;
    private List<AudioBean> mAudioBeans;
    private AudioBean mDelAudioBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_manager);

        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.w("onItemDragStart", "drag start");
                final BaseViewHolder holder = ((BaseViewHolder) viewHolder);

                // 开始时，item背景色变化，demo这里使用了一个动画渐变，使得自然
                int startColor = Color.WHITE;
                int endColor = Color.rgb(245, 245, 245);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
                    v.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            holder.itemView.setBackgroundColor((int)animation.getAnimatedValue());
                        }
                    });
                    v.setDuration(300);
                    v.start();
                }
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                /*Log.d(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());*/
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                /*Log.d(TAG, "drag end");*/
                final BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                // 结束时，item背景色变化，demo这里使用了一个动画渐变，使得自然
                int startColor = Color.rgb(245, 245, 245);
                int endColor = Color.WHITE;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
                    v.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            holder.itemView.setBackgroundColor((int)animation.getAnimatedValue());
                        }
                    });
                    v.setDuration(300);
                    v.start();
                }
            }
        };

        // 侧滑监听
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.e("onItemSwipeStart", "pos = " + pos);
                mDelAudioBean = mAdapter.getItem(pos);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                /*Log.d(TAG, "View reset: " + pos);*/
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.e("onItemSwiped", "pos = " + pos);
                Log.e("onItemSwiped", mDelAudioBean.toString());
                boolean delDaoResult = mUserBeanDaoUtils.deleteAudioBean(mDelAudioBean);

                Log.e("onItemSwiped", "delDaoResult: " + delDaoResult);

                boolean delResult = FileUtils.deleteSingleFile(mDelAudioBean.getLocalPath());
                Log.e("onItemSwiped", "delResult: " + delResult);
                mAudioBeans = mUserBeanDaoUtils.queryAllAudioBean();
                mAdapter.setNewInstance(mAudioBeans);

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(AudioManagerActivity.this, android.R.color.holo_blue_light));
            }
        };

        mUserBeanDaoUtils = new UserBeanDaoUtils(getApplicationContext());
        mAudioBeans = mUserBeanDaoUtils.queryAllAudioBean();

        mAdapter = new DragAndSwipeAdapter(mAudioBeans);

        mAdapter.getDraggableModule().setSwipeEnabled(true);
        mAdapter.getDraggableModule().setDragEnabled(true);
        mAdapter.getDraggableModule().setOnItemDragListener(listener);
        mAdapter.getDraggableModule().setOnItemSwipeListener(onItemSwipeListener);
        mAdapter.getDraggableModule().getItemTouchHelperCallback().setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        //mAdapter.getDraggableModule().getItemTouchHelperCallback().setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Log.e("onItemClick", "点击了：" + position + adapter.getItem(position).toString());

            }
        });
    }
}