package com.example.lindroidcode.anim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class AnimActivity extends AppCompatActivity {

    private TextView mTvAnimIntro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        mTvAnimIntro = findViewById(R.id.tv_anim_intro);

        findViewById(R.id.bt_anim_alpha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation animation = new AlphaAnimation(0, 1);// 透明度0变化到透明度为1
                animation.setDuration(1000);// 动画执行时间1s
                mTvAnimIntro.startAnimation(animation);
            }
        });
        findViewById(R.id.bt_anim_alpha_from_xml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation= AnimationUtils.loadAnimation(AnimActivity.this, R.anim.anim_alpha);
                mTvAnimIntro.startAnimation(animation);
            }
        });
        findViewById(R.id.bt_anim_rotate_from_xml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation= AnimationUtils.loadAnimation(AnimActivity.this, R.anim.anim_rotate);
                mTvAnimIntro.startAnimation(animation);
            }
        });
        findViewById(R.id.bt_anim_scale_from_xml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation= AnimationUtils.loadAnimation(AnimActivity.this, R.anim.anim_scale);
                mTvAnimIntro.startAnimation(animation);
            }
        });
        findViewById(R.id.bt_anim_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.setDuration(1000);

                AlphaAnimation alpha=new AlphaAnimation(0,1);
                alpha.setDuration(1000);
                animationSet.addAnimation(alpha);

                TranslateAnimation translate = new TranslateAnimation(100, 200, 0, 200);
                translate.setDuration(1000);
                animationSet.addAnimation(translate);
                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mTvAnimIntro.startAnimation(animationSet);
            }
        });
    }
}
