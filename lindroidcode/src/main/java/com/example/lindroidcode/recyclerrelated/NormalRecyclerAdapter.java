package com.example.lindroidcode.recyclerrelated;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lindroidcode.R;

import java.util.ArrayList;
import java.util.List;

public class NormalRecyclerAdapter extends RecyclerView.Adapter<NormalViewHolder> {

    private List<String> mDataSet;
    private Context mContext;

    NormalRecyclerAdapter(Context context){
        mContext = context;
        mDataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public NormalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_normal_text,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NormalViewHolder normalViewHolder, int position) {
        TextView tvNormal = (TextView) normalViewHolder.getView(R.id.tv_normal_text_msg);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(String.valueOf(position)).append(" ");
        builder.append(mDataSet.get(position));
        int colorRes;
        switch (position % 4) {
            case 0:
                colorRes = android.R.color.holo_red_light;
                break;
            case 1:
                colorRes = android.R.color.holo_green_light;
                break;
            case 2:
                colorRes = android.R.color.holo_blue_light;
                break;
            case 3:
                colorRes = android.R.color.holo_purple;
                break;
            default:
                colorRes = android.R.color.black;
                break;
        }
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext, colorRes));
        builder.setSpan(colorSpan, 0, String.valueOf(position).length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, String.valueOf(position).length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tvNormal.setText(builder);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public synchronized void addItem(String msg) {
        mDataSet.add(msg);
        notifyItemInserted(getItemCount());
    }

    public synchronized void addItemList(List<String> list) {
        int startPos = getItemCount();
        if (list != null) {
            mDataSet.addAll(list);
            notifyItemRangeInserted(startPos, list.size());
        }
    }
}
