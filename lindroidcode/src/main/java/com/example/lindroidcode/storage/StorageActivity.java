package com.example.lindroidcode.storage;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lindroidcode.R;

import java.io.File;

public class StorageActivity extends AppCompatActivity {

    private static final String TAG = StorageActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        String fileInnerName = "/inner/img"; //目录名

        File fileCache = new File(getCacheDir(), fileInnerName);
        //
        if (!fileCache.exists()) {
            boolean isInner = fileCache.mkdirs();

            /**
             * createNewFile:新建文件（非目录）
             * mkdir：新建目录
             * mkdirs：新建目录，与mkdir的区别是：比如mkdirs（“D:/test/test2”) 如果test不存在会创建，然后创建test2，如果test不存在，会失败。
             */
        }
        Log.e(TAG,  "  " + fileCache.getAbsolutePath());

        TextView tvCacheDir = findViewById(R.id.tv_cache_dir);

        tvCacheDir.append("\ngetCacheDir().getAbsolutePath(): ");
        tvCacheDir.append(getCacheDir().getAbsolutePath());
        tvCacheDir.append("\ngetCacheDir().getPath(): ");
        tvCacheDir.append(getCacheDir().getPath());
        tvCacheDir.append("\nAS中的Device File Explorer是 /data/data/com.example.lindroidcode/cache");

        TextView tvFileDir = findViewById(R.id.tv_file_dir);

        tvFileDir.append("\ngetFilesDir().getAbsolutePath(): ");
        tvFileDir.append(getFilesDir().getAbsolutePath());
        tvFileDir.append("\n读写该目录API: \nopenFileOutput(String,int);\nopenFileInput(String name); ");

        findViewById(R.id.bt_external_cache_dir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StorageActivity.this,ExternalActivity.class));
            }
        });
    }
}
