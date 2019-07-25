package com.example.rectview;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.R;

public class DrawRectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_rect);

        FrameLayout frameLayout = findViewById(android.R.id.content);
        ConstraintLayout constraintLayout = (ConstraintLayout) frameLayout.getChildAt(0);

        RectView gameView = new RectView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        gameView.setLayoutParams(layoutParams);
        constraintLayout.addView(gameView);

    }
}
