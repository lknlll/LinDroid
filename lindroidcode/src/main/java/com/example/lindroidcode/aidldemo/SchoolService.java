package com.example.lindroidcode.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;

import com.example.lindroidcode.MainActivity;

public class SchoolService extends Service {

    private SchoolBinder mSchoolBinder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSchoolBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSchoolBinder = new SchoolBinder();
        startActivity(new Intent(this, MainActivity.class));
    }

    private class SchoolBinder extends ISchool.Stub{
        @Override
        public String getSchoolName() throws RemoteException {
            return "Demo School Name";
        }

        @Override
        public int getStudentNum() throws RemoteException {
            return 3000;
        }

        @Override
        public Student getStudent() throws RemoteException {
            Student student = new Student();
            student.setName("demoStudentName");
            student.setAge(18);
            return student;
        }
    }
}
