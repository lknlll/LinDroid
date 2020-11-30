package com.example.lindroidcode.recyclerrelated.quickadapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.lindroidcode.R;
import com.example.lindroidcode.greendaoatoz.beans.AudioBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DragAndSwipeAdapter extends BaseQuickAdapter<AudioBean, BaseViewHolder> implements DraggableModule {

    public DragAndSwipeAdapter(List<AudioBean> data) {
        super(R.layout.item_draggable_view, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull AudioBean audioBean) {
        if (audioBean.getMarks() != null) {
            helper.setText(R.id.tv_mark_count,"标记数：" + audioBean.getMarks().size());
        }
        String item = audioBean.getLocalPath();
        if (item != null && item.contains("audio_")) {
            String[] sA = item.split("audio_");
            helper.setText(R.id.tv, sA[1]);
        }
    }
}
