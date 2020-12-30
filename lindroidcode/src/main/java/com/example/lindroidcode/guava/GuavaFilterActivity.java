package com.example.lindroidcode.guava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.lindroidcode.databinding.ActivityGuavaFilterBinding;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

public class GuavaFilterActivity extends AppCompatActivity {

    private ActivityGuavaFilterBinding mActivityGuavaFilterBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityGuavaFilterBinding = ActivityGuavaFilterBinding.inflate(getLayoutInflater());
        View view = mActivityGuavaFilterBinding.getRoot();
        setContentView(view);

        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        //谓词通常用于过滤数据
        Iterable<String> result = Iterables.filter(names, Predicates.containsPattern("a"));

        Log.e(GuavaFilterActivity.class.getSimpleName(),"result " + result);
    }
}