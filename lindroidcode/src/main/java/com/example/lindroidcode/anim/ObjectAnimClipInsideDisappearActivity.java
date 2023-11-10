package com.example.lindroidcode.anim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.lindroidcode.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class ObjectAnimClipInsideDisappearActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mSplashIv;
    private ConstraintLayout mCLRoot;

    private int bitMapH,bitMapW;

    int h;
    int w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_anim_clip_inside_disappear);

        mSplashIv = (ImageView) findViewById(R.id.splash_image);
        mSplashIv.setOnClickListener(this);
        mCLRoot = (ConstraintLayout)findViewById(R.id.cl_root);

        Bitmap bmSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.iv_splash);

        bitMapH = bmSrc.getHeight();
        bitMapW = bmSrc.getWidth();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("ObjectAnimTAG", "areAnimatorsEnabled: " + ValueAnimator.areAnimatorsEnabled());
            Toast.makeText(ObjectAnimClipInsideDisappearActivity.this,ValueAnimator.areAnimatorsEnabled()+"",Toast.LENGTH_SHORT).show();
        }
    }

    private void animateSplashScale(){

        h = mCLRoot.getMeasuredHeight();
        w = mCLRoot.getMeasuredWidth();

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）

        float rate = (float) w/(float)bitMapW - 0.05f;


        final ObjectAnimator anim = ObjectAnimator.ofFloat(mSplashIv, "alpha", 1f, 0f);
        // 表示的是:
        // 动画作用对象是mButton
        // 动画作用的对象的属性是透明度alpha
        // 动画效果是:常规 - 全透明 - 常规
        // ofFloat()作用有两个
        anim.setDuration(2300);
        // 设置动画运行的时长

        /*anim.setStartDelay(500);*/
        // 设置动画延迟播放时间

        /*anim.setRepeatCount(0);*/
        // 设置动画重复播放次数 = 重放次数+1
        // 动画播放次数 = infinite时,动画无限重复

        /*anim.setRepeatMode(ValueAnimator.RESTART);*/
        // 设置重复播放动画模式
        // ValueAnimator.RESTART(默认):正序重放
        // ValueAnimator.REVERSE:倒序回放
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        final ObjectAnimator clipAnim = ObjectAnimator.ofObject(mSplashIv,"clipBounds",
                new RectEvaluator(),
                new Rect(0,0,bitMapW,bitMapH),
                new Rect(0,800,bitMapW,h-600)
        );
        clipAnim.setDuration(2500);
        clipAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        clipAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                anim.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


//        ObjectAnimator scale = ObjectAnimator.ofFloat(mSplashIv, "scaleX", new float[]{1f, 2f, 3f, 4f,5f,6f,1f});
        ObjectAnimator scale = ObjectAnimator.ofFloat(mSplashIv, "scaleX", 1f, rate);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mSplashIv, "scaleY", 1f, rate);
        AnimatorSet set = new AnimatorSet();

        scale.setDuration(2500);
        scaleY.setDuration(2500);
//        scale.start();
        set.playTogether(scale,scaleY,clipAnim);
        set.start();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.splash_image) {
            animateSplashScale();
        }
    }
}