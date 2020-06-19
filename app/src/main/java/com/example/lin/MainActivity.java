package com.example.lin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.R;
import com.example.aidldemo.AidlClientActivity;
import com.example.opengleffect.EffectsFilterActivity;
import com.example.rectview.DrawRectActivity;
import com.lucasurbas.listitemview.ListItemView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListItemView drawRect = findViewById(R.id.draw_rect);

        drawRect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawRectActivity.class);
                startActivity(intent);
            }
        });
        ListItemView gl = findViewById(R.id.gl);
        gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, EffectsFilterActivity.class);
                startActivity(intent);
            }
        });
        ListItemView asyncTaskLoader = findViewById(R.id.async_task_Loader);
        asyncTaskLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, EffectsFilterActivity.class);//Todo finish appList learning
                startActivity(intent);
            }
        });
        ListItemView aidlDemo = findViewById(R.id.aidl_demo);
        aidlDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AidlClientActivity.class);
                startActivity(intent);
            }
        });
    }
}
