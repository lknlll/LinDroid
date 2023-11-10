package com.example.lindroidcode.recyclerrelated;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.recyclerrelated.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

public class NormalHotAddressRecyclerAdapter extends RecyclerView.Adapter<NormalViewHolder> {

    private static final String TAG = NormalHotAddressRecyclerAdapter.class.getSimpleName();
    private List<AddressBean> mDataSet;
    private Context mContext;

    NormalHotAddressRecyclerAdapter(Context context){
        mContext = context;
        mDataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public NormalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_hot_spots_text,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NormalViewHolder normalViewHolder, int position) {

        Log.e(TAG, "onBindViewHolder bind: " + position);
        TextView tvNormal = (TextView) normalViewHolder.getView(R.id.tv_address_name);
        TextView tvType = (TextView) normalViewHolder.getView(R.id.tv_address_type);
        TextView tvDistance = (TextView) normalViewHolder.getView(R.id.tv_address_distance);

        if (mDataSet != null && mDataSet.get(position) != null) {

            AddressBean addressBean = mDataSet.get(position);
            tvNormal.setText(addressBean.getAddressName());

            if (addressBean.getAddressType()!= null && addressBean.getAddressType().length()> 0) {
                if (addressBean.getAddressType().equals("MALL")) {

                    tvType.setText("商业街");
                    tvType.setTextColor(Color.parseColor("#FE7100"));
                    GradientDrawable bg4 = (GradientDrawable) tvType.getBackground();
                    //修改填充色
                    bg4.setColor(Color.parseColor("#FEF0E5"));
                }else if (addressBean.getAddressType().equals("SCENE")) {

                    tvType.setText("景区");
                    tvType.setTextColor(Color.parseColor("#FF4B33"));
                    GradientDrawable bg4 = (GradientDrawable) tvType.getBackground();
                    //修改填充色
                    bg4.setColor(Color.parseColor("#FFEDEB"));
                }
                tvType.setVisibility(View.VISIBLE);
            }else {
                tvType.setVisibility(View.GONE);
            }
            
            tvDistance.setText(addressBean.getAddressDistance());
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public synchronized void addItem(AddressBean msg) {
        mDataSet.add(msg);
        notifyItemInserted(getItemCount());
    }

    public synchronized void addItemList(List<AddressBean> list) {
        int startPos = getItemCount();
        if (list != null) {
            mDataSet.addAll(list);
            notifyItemRangeInserted(startPos, list.size());
        }
    }
}
