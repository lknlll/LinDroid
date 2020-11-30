package com.example.lindroidcode.activityrelated.lifecycle;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.lindroidcode.R;

public class AActivity extends AppCompatActivity {

    private static final String TAG = AActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Log.e(TAG, "onCreate: A");

        findViewById(R.id.bt_start_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AActivity.this, BActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                //FLAG_ACTIVITY_REORDER_TO_FRONT，如果B之前没有创建才走onCreate，如果已经创建了，则只会把B从Activity栈中搬到A的上面来，而不再走onCreate。
                startActivity(intent);
            }
        });
    }


}
