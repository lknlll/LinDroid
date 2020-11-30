package com.example.opengleffect;

import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.R;

public class EffectsFilterActivity extends AppCompatActivity {

    private GLSurfaceView mEffectView;

    private TextureRenderer renderer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects_filter);

        renderer = new TextureRenderer();
        renderer.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.puppy));
        renderer.setCurrentEffect(R.id.none);

        mEffectView = (GLSurfaceView) findViewById(R.id.effectsview);
        mEffectView.setEGLContextClientVersion(2);
        mEffectView.setRenderer(renderer);
        mEffectView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("info", "menu create");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        renderer.setCurrentEffect(item.getItemId());
        mEffectView.requestRender();
        return true;
    }
}
