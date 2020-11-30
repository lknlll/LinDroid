package com.example.lindroidcode.recyclerrelated;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lindroidcode.R;

import java.util.List;

public class NormalListViewAdapter extends ArrayAdapter {

    public NormalListViewAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String content = (String)getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_normal_text, null);
        TextView tvNormal = (TextView) view.findViewById(R.id.tv_normal_text_msg);


        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(String.valueOf(position)).append(" ");
        builder.append(content);
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
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(getContext(), colorRes));
        builder.setSpan(colorSpan, 0, String.valueOf(position).length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, String.valueOf(position).length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tvNormal.setText(builder);
        return view;
    }
}
