package com.example.lindroidcode.anim;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.utils.DensityUtils;

public class AnimActivity extends AppCompatActivity {

    private TextView mTvAnimIntro;
    private TextView mTvAnimSeries;
    private ImageView mIvFormer;
    private ImageView mIvLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        mTvAnimIntro = findViewById(R.id.tv_anim_intro);
        mTvAnimSeries = findViewById(R.id.tv_anim_series);
        mIvFormer = findViewById(R.id.iv_former);
        mIvLater = findViewById(R.id.iv_later);

        String linkDesp = "AndroidMaterialAnimationPractise";

        SpannableString msp = new SpannableString(linkDesp);
        msp.setSpan(new URLSpan("https://github.com/SherlockShi/AndroidMaterialAnimationPractise"), 0, linkDesp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //网络

        mTvAnimSeries.setText(msp);
        mTvAnimSeries.setMovementMethod(LinkMovementMethod.getInstance());

        findViewById(R.id.bt_object_anim_clip_inside_disappear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimActivity.this,ObjectAnimClipInsideDisappearActivity.class));
            }
        });
        findViewById(R.id.bt_object_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long translationDuration = 400;//位移时长
                long alphaDuration1 = 200;//icon1 透明度时长
                long alphaDuration2 = 250;//icon2 透明度时长
                long delayed = 2400;// 延迟时长

                ObjectAnimator animFormer = ObjectAnimator.ofFloat(mIvFormer, "alpha", 1f, 0f).setDuration(alphaDuration1);//icon1 循环一 透明度动画 逐渐消失
                ObjectAnimator animLater = ObjectAnimator.ofFloat(mIvLater, "alpha", 0f, 1f).setDuration(alphaDuration2);//icon2 循环一 透明度动画 逐渐出现

                //icon1 循环一 位移动画 从parent内上移20
                ObjectAnimator animFormerTranslation = ObjectAnimator.ofFloat(mIvFormer, "translationY", 0, -DensityUtils.dp2px(20,AnimActivity.this)).setDuration(translationDuration);
                //icon2 循环一 位移动画 从parent下方20的位置上移20完整进入parent内
                ObjectAnimator animLaterTranslation = ObjectAnimator.ofFloat(mIvLater, "translationY", DensityUtils.dp2px(20,AnimActivity.this),0).setDuration(translationDuration);

                AnimatorSet set = new AnimatorSet();

                /*animFormer.setRepeatMode(ValueAnimator.RESTART);
                animLater.setRepeatMode(ValueAnimator.RESTART);
                animFormerTranslation.setRepeatMode(ValueAnimator.RESTART);
                animLaterTranslation.setRepeatMode(ValueAnimator.RESTART);

                animFormer.setRepeatCount(ValueAnimator.INFINITE);
                animLater.setRepeatCount(ValueAnimator.INFINITE);
                animFormerTranslation.setRepeatCount(ValueAnimator.INFINITE);
                animLaterTranslation.setRepeatCount(ValueAnimator.INFINITE);*/
                set.playTogether(animFormer,animLater,animFormerTranslation,animLaterTranslation);

                ObjectAnimator animFormer2 = ObjectAnimator.ofFloat(mIvLater, "alpha", 1f, 0f).setDuration(alphaDuration2);//icon2 循环二 透明度动画 逐渐消失
                ObjectAnimator animLater2 = ObjectAnimator.ofFloat(mIvFormer, "alpha", 0f, 1f).setDuration(alphaDuration1);//icon1 循环二 透明度动画 逐渐出现

                //icon2 循环二 位移动画 从parent内上移20
                ObjectAnimator animFormerTranslation2 = ObjectAnimator.ofFloat(mIvLater, "translationY", 0, -DensityUtils.dp2px(20,AnimActivity.this)).setDuration(translationDuration);
                //icon1 循环二 位移动画 从parent下方20的位置上移20完整进入parent内
                ObjectAnimator animLaterTranslation2 = ObjectAnimator.ofFloat(mIvFormer, "translationY", DensityUtils.dp2px(20,AnimActivity.this),0).setDuration(translationDuration);

                AnimatorSet set2 = new AnimatorSet();

                set2.playTogether(animFormer2,animLater2,animFormerTranslation2,animLaterTranslation2);

                set2.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        mIvLater.bringToFront();
                        set.setStartDelay(delayed);//设置延迟
                        set.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                /*set.start();*/

                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mIvFormer.bringToFront();
                        set2.setStartDelay(delayed);//设置延迟
                        set2.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                set.start();
            }
        });

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
