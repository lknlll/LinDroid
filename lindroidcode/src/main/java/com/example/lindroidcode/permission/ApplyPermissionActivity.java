package com.example.lindroidcode.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

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

        if (!isGranted) {

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.msg_perm_tip)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ApplyPermissionActivity.this,HandlePermissionActivity.class);
                            startActivity(intent);
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
