package com.example.aidldemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.R;
import com.example.lindroidcode.aidldemo.ISchool;

public class AidlClientActivity extends AppCompatActivity {

    private static final String TAG = AidlClientActivity.class.getSimpleName();
    private ISchool iSchool;

    private Intent intent;

    private TextView schoolText;

    private boolean isBind = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iSchool = ISchool.Stub.asInterface(service);
            isBind = true;
            Log.e(TAG, "onServiceConnected: name" + name.getClassName() );
            try {
                Log.e(TAG, "onServiceConnected: service" + service.getInterfaceDescriptor() );
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            Log.e(TAG, "onServiceDisconnected: name" + name.getClassName() );
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.e(TAG, "onBindingDied: name" + name.getClassName());
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.e(TAG, "onNullBinding: name" + name.getClassName() );
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_client);
        schoolText = (TextView) findViewById(R.id.tv_school);
        intent = new Intent();
        //Server Manifest 中注册的action
        intent.setAction("com.example.lindroidcode.aidldemo.school");
        //Server Application 包名，注意，不是Service文件的路径
        intent.setPackage("com.example.lindroidcode");

        boolean bindResult = bindService(intent, connection, Service.BIND_AUTO_CREATE);
        Log.e(TAG, "onCreate: " + bindResult );
    }

    public void getSchool(View view) {
        try {
            if (!isBind) {
                boolean bindResult = bindService(intent, connection, Service.BIND_AUTO_CREATE);
                Log.e(TAG, "getSchool: " + bindResult);
            }
            schoolText.setText("schoolName:" + iSchool.getSchoolName() + "\nschoolNum:" + iSchool.getStudentNum() + "\nstudent:" + iSchool.getStudent());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}