package com.example.lindroidcode.textviewrelated;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class EditTextDemoActivity extends AppCompatActivity {

    private static final String TAG = EditTextDemoActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_demo);

        EditText et = (EditText) findViewById(R.id.et_test_watcher);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: s = " + s + ", start = " + start +
                        ", count = " + count + ", after = " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: s = " + s + ", start = " + start +
                        ", before = " + before + ", count = " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + s);
            }
        };
        et.addTextChangedListener(watcher);

        TextView tvEtApi = findViewById(R.id.tv_et_api);
        tvEtApi.append("\n不要在Watcher回调中setText 之类，避免死循环");
        tvEtApi.append("\n设置选中： editText.setSelection(0, lastIndexOf);");
        tvEtApi.append("\n实时监听光标的位置变化: 自定义EditText override onSelectionChanged()");

    }
}