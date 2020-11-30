package com.example.lindroidcode.handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class HandlerOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_overview);

        TextView tvObtain  = findViewById(R.id.tv_obtain);
        tvObtain.append("\nbecause if there is already an Message object that not be used by any one, the system will hand use that object, so you don't have to create and object and allocate memory. it is also another example of object recycling and reusing in android.");
        tvObtain.append("\nanother way to send Message");
        tvObtain.append("\nMessage msg = handler.obtainMessage();");
        tvObtain.append("\nmsg.sendToTarget();");
    }
}