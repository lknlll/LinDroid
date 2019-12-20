package com.example.lindroidcode.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lindroidcode.R;

import java.util.List;

public class HandlePermissionActivity extends AppCompatActivity implements PermissionHelper.PermissionCallbacks {

    private static final String TAG = HandlePermissionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionHelper.requestPermissions(this, getString(R.string.msg_perm_tip),
                PermissionHelper.REQUEST_CODE_PERMISSION, false,Manifest.permission.CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e(TAG, "onRequestPermissionsResult: " );
        if (PermissionHelper.getPermissionStatusListener() != null) {
            PermissionHelper.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
        }else {
            Log.e(TAG, "onRequestPermissionsResult: no result listener" );
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        for (int i = 0; i < perms.size(); i++) {
            Log.e(TAG, "onPermissionsGranted: " + perms.get(i) );
        }
    }

    @Override
    public void onDialogCancelledOnClick() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.msg_perm_tip)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionHelper.getPermissionStatusListener().onDenied();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionHelper.getPermissionStatusListener().onDenied();
                        finish();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        boolean isNeverAskedAgain = PermissionHelper.checkDeniedPermissionsNeverAskAgain(this,
//                getString(R.string.msg_perm_denied),
//                R.string.setting, R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        PermissionHelper.getPermissionStatusListener().onDenied();
//                        finish();
//                    }
//                }, perms);
//        if (!isNeverAskedAgain) {
//            PermissionHelper.getPermissionStatusListener().onDenied();
//            finish();
//        }
        PermissionHelper.getPermissionStatusListener().onDenied();
        finish();
    }

    @Override
    public void onPermissionsAllGranted() {
        Log.e(TAG, "onPermissionsAllGranted");
        PermissionHelper.getPermissionStatusListener().onGranted();
        finish();
    }
    //Todo never ask again
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.e(TAG, "onActivityResult: " + requestCode + resultCode);
//        if (requestCode == PermissionHelper.SETTINGS_REQ_CODE) {
//            //设置返回
//            boolean isGranted = PermissionHelper.hasPermissions(this, Manifest.permission.CAMERA);
//            if (!isGranted) {
//                PermissionHelper.requestPermissions(this, getString(R.string.msg_perm_tip),
//                        PermissionHelper.REQUEST_CODE_PERMISSION, false,Manifest.permission.CAMERA);
//            }else{
//                PermissionHelper.getPermissionStatusListener().onGranted();
//                finish();
//            }
//        }
//    }

    @Override
    protected void onDestroy() {
        PermissionHelper.dropPermissionStatusListener();
        super.onDestroy();
    }
}
