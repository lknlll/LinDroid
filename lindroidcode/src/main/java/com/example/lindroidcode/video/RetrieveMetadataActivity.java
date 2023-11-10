package com.example.lindroidcode.video;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lindroidcode.R;
import com.example.lindroidcode.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class RetrieveMetadataActivity extends AppCompatActivity {

    public static final String TAG = RetrieveMetadataActivity.class.getSimpleName();
    public static final int REQUEST_CODE_VIDEO_GALLERY = 10301;
    public static final String THREAD_NAME_VIDEO_TRANSFER = "video_transfer_thread";

    private TextView mTvWidthHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_metadata);

        mTvWidthHeight = findViewById(R.id.tv_width_height);
        TextView tvRetrieveMetadataIntro = findViewById(R.id.tv_r_m_api_intro);

        tvRetrieveMetadataIntro.append("android.media.MediaMetadataRetriever\n");
        tvRetrieveMetadataIntro.append(" MediaMetadataRetriever类提供了用于从输入媒体文件检索帧和元数据的统一接口。\n");
        tvRetrieveMetadataIntro.append(" 用来获取本地和网络media相关文件的信息\n");
        tvRetrieveMetadataIntro.append(" works in API level 10 and up\n");

        findViewById(R.id.bt_select_video).setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("video/*");
            if (pickIntent.resolveActivity(RetrieveMetadataActivity.this.getPackageManager()) == null) {
                Toast.makeText(RetrieveMetadataActivity.this, "未找到可用的图库应用", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivityForResult(pickIntent, REQUEST_CODE_VIDEO_GALLERY);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VIDEO_GALLERY) {
            Log.e(TAG, "onActivityResult: " + data.getData());
            Executors.newSingleThreadExecutor(r -> {
                Thread thread = new Thread(r,THREAD_NAME_VIDEO_TRANSFER);
                thread.setDaemon(false);
                return thread;
            }).execute(() -> {
                File file = FileUtils.transfer(RetrieveMetadataActivity.this,data.getData());
                if (file == null) {
                    return;
                }

                //Todo compress

                // Get the video width
                int mVideoWidth = getVideoWidthOrHeight(file, "width");
                // Get the video height
                int mVideoHeight = getVideoWidthOrHeight(file, "height");
                runOnUiThread(() -> {

                    mTvWidthHeight.append("\n");
                    mTvWidthHeight.append("" + mVideoWidth);
                    mTvWidthHeight.append("\n");
                    mTvWidthHeight.append("" + mVideoHeight);

                });
            });
        }
    }

    public int getVideoWidthOrHeight(File file, String widthOrHeight) {
        MediaMetadataRetriever retriever = null;
        Bitmap bmp = null;
        FileInputStream inputStream = null;
        int mWidthHeight = 0;
        try {
            retriever = new  MediaMetadataRetriever();
            inputStream = new FileInputStream(file.getAbsolutePath());
            retriever.setDataSource(inputStream.getFD());
            bmp = retriever.getFrameAtTime();
            if (widthOrHeight.equals("width")){
                mWidthHeight = bmp.getWidth();
            }else {
                mWidthHeight = bmp.getHeight();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally{
            if (retriever != null){
                retriever.release();
            }if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mWidthHeight;
    }
}