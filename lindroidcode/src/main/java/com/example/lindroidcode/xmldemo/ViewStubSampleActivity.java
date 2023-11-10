package com.example.lindroidcode.xmldemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lindroidcode.R;

/**
 * Created by CnPeng on 2017/1/11. ViewStub 的使用示例
 */

public class ViewStubSampleActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ViewStubSampleActivity.class.getSimpleName();
    private ViewStub viewStub;
    private TextView hintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub_sample);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        viewStub = (ViewStub) findViewById(R.id.viewstub_test);

        Button btn_show = (Button) findViewById(R.id.btn_vs_showView);
        Button btn_hide = (Button) findViewById(R.id.btn_vs_hideView);
        Button btn_change = (Button) findViewById(R.id.btn_vs_changeHint);

        btn_show.setOnClickListener(this);
        btn_hide.setOnClickListener(this);
        btn_change.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_vs_showView:

                //inflate 方法只能被调用一次，因为调用后viewStub对象就被移除了视图树；
                // 所以，如果此时再次点击显示按钮，就会崩溃，错误信息：ViewStub must have a non-null ViewGroup viewParent；
                // 所以使用try catch ,当此处发现exception 的时候，在catch中使用setVisibility()重新显示
                try {
                    View iv_vsContent = viewStub.inflate();     //inflate 方法只能被调用一次，
                    hintText = (TextView) iv_vsContent.findViewById(R.id.tv_vsContent);
                    //                    hintText.setText("没有相关数据，请刷新");
                } catch (Exception e) {
                    Log.e(TAG, "viewStub.inflate exception: " +e.getMessage());
                    viewStub.setVisibility(View.VISIBLE);
                } finally {
                    hintText.setText("没有相关数据，请刷新");
                }
                break;
            case R.id.btn_vs_hideView:  //如果显示
                viewStub.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_vs_changeHint:
                if (hintText!=null) {
                    hintText.setText("网络异常，无法刷新，请检查网络");
                }
                break;
        }
    }
}

