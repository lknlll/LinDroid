package com.example.lindroidcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mEditSelection;
    private Button mButtonGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditSelection = findViewById(R.id.edit_selection);
        mButtonGo = findViewById(R.id.bt_go);

        mButtonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sSelection = mEditSelection.getEditableText().toString();
                int numSelection = Integer.parseInt(sSelection);
                String[] sToClasses = getResources().getStringArray(R.array.class_names);

                try {
                    Class toClass = Class.forName(sToClasses[numSelection-1]);
                    startActivity(new Intent(MainActivity.this,toClass));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
