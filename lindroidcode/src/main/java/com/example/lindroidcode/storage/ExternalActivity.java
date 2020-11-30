package com.example.lindroidcode.storage;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.permission.PermissionHelper;
import com.example.lindroidcode.permission.PermissionStatusListener;

public class ExternalActivity extends AppCompatActivity {

    private TextView mTvPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);

        mTvPermission = findViewById(R.id.tv_permission_situation);
        boolean isGranted = PermissionHelper.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (!isGranted) {

            mTvPermission.setText("external permission needed.");
            final PermissionStatusListener permissionStatusListener = new PermissionStatusListener() {
                @Override
                public void onGranted() {
                    mTvPermission.setText("external permission granted.");
                }

                @Override
                public void onDenied() {
                    mTvPermission.setText("external permission denied.");
                }
            };

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.msg_perm_tip)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            PermissionHelper.startPermissionApplyForResult(ExternalActivity.this,permissionStatusListener, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }
                    })
                    .create();
            alertDialog.show();
        }else {
            mTvPermission.setText("external permission satisfied.");
        }
        TextView tvExternalCacheDir = findViewById(R.id.tv_external_cache_dir);

        tvExternalCacheDir.append("\ngetExternalCacheDir().getAbsolutePath(): ");
        String state;
        state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)) {
            if (getExternalCacheDir() != null) {
                tvExternalCacheDir.append(getExternalCacheDir().getAbsolutePath());
            }
        }
        TextView tvExternalFileDir = findViewById(R.id.tv_external_file_dir);

        tvExternalFileDir.append("\ngetExternalFileDir().getAbsolutePath(): ");
        if(state.equals(Environment.MEDIA_MOUNTED)) {
            if (getExternalFilesDir(null) != null) {
                tvExternalFileDir.append(getExternalFilesDir(null).getAbsolutePath());
            }
        }
        TextView tvExternalStorageDir = findViewById(R.id.tv_external_storage_dir);

        tvExternalStorageDir.append("\nEnvironment.getExternalStorageDirectory().getAbsolutePath(): ");
        if(state.equals(Environment.MEDIA_MOUNTED)) {
            tvExternalStorageDir.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            tvExternalStorageDir.append("官方建议，不要直接使用该目录，为了避免污染用户的根命名空间，应用私有的数据，应该放在 Context.getExternalFilesDir目录下\n" +
                    "其他的可以被分享的文件，可以放在getExternalStoragePublicDirectory(String s)目录下");
        }
    }
}
