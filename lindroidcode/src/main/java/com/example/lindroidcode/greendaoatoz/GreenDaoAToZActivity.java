package com.example.lindroidcode.greendaoatoz;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.greendaoatoz.beans.UserBean;
import com.example.lindroidcode.greendaoatoz.common.UserBeanDaoUtils;

public class GreenDaoAToZActivity extends AppCompatActivity {

    UserBeanDaoUtils mUserBeanDaoUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao_a_to_z);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mUserBeanDaoUtils = new UserBeanDaoUtils(getApplicationContext());
        mUserBeanDaoUtils.insertUserBean(new UserBean(null, "Google",
                "http://7xi8d6.48096_n.jpg"));

        TextView tvNotes = findViewById(R.id.tv_hello_green_d_a_o);
        tvNotes.append("\nPropertyConverter");
        tvNotes.append("\nList存数据库");
        tvNotes.append("\n//将数据库中的值，转化为实体Bean类对象(比如List<String>)\n" +
                "P convertToEntityProperty(D databaseValue);\n" +
                "//将实体Bean类(比如List<String>)转化为数据库中的值(比如String)\n" +
                "D convertToDatabaseValue(P entityProperty);");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_manage_local_audios){
            startActivity(new Intent(GreenDaoAToZActivity.this, AudioManagerActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
