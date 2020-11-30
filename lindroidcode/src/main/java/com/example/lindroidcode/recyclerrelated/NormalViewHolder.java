package com.example.lindroidcode.recyclerrelated;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class NormalViewHolder extends RecyclerView.ViewHolder {
    @NonNull
    private SparseArray<View> mViews;

    public NormalViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }


    private View findViewById(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    protected View getView(@IdRes int viewId) {
        return findViewById(viewId);
    }
}
