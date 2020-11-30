package com.example.lindroidcode.encryptdecrypt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lindroidcode.R;

public class AesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes);

        TextView tvAes = findViewById(R.id.tv_aes_overview);
        final TextView tvResult = findViewById(R.id.tv_aes_result);
        tvAes.append("\n加密和解密用相同的密钥");
        tvAes.append("\n支持以下加密算法: AES / DES / DESede / RSA");

        final EditText etPlain = findViewById(R.id.et_plain);
        findViewById(R.id.bt_encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String encrypted = AESUtils.encrypt(etPlain.getEditableText().toString().getBytes());
                    tvResult.append(encrypted);
                    String decrypted = AESUtils.decrypt(encrypted.getBytes());
                    tvResult.append("\nDecoded:");
                    tvResult.append(decrypted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}