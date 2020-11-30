package com.example.lindroidcode;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEditSelection;
    private Button mButtonGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] sToClasses = getResources().getStringArray(R.array.class_names);

        mEditSelection = findViewById(R.id.edit_selection);
        mButtonGo = findViewById(R.id.bt_go);

        mEditSelection.setText(sToClasses.length + "");
        mButtonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sSelection = mEditSelection.getEditableText().toString();
                int numSelection = Integer.parseInt(sSelection);

                try {
                    Class toClass = Class.forName(sToClasses[numSelection-1]);//类名进行类初始化
                    startActivity(new Intent(MainActivity.this,toClass));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        TextView tvInstruction = findViewById(R.id.text_instruction);
        tvInstruction.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


}
