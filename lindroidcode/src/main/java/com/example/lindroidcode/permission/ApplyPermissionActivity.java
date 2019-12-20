package com.example.lindroidcode.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class ApplyPermissionActivity extends AppCompatActivity {

    private CheckBox mCheckPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_permission);
        mCheckPermission = findViewById(R.id.check_flag_permission);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isGranted = PermissionHelper.hasPermissions(this, Manifest.permission.CAMERA);

        mCheckPermission.setChecked(isGranted);

        final TextView textPermissionListener = findViewById(R.id.text_permission_listener);

        if (!isGranted) {

            final PermissionStatusListener permissionStatusListener = new PermissionStatusListener() {
                @Override
                public void onGranted() {
                    textPermissionListener.setText(R.string.text_permission_granted);
                }

                @Override
                public void onDenied() {
                    textPermissionListener.setText(R.string.text_permission_denied);
                }
            };

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.msg_perm_tip)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            PermissionHelper.startPermissionApplyForResult(ApplyPermissionActivity.this,permissionStatusListener);
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
        }

    }
}
