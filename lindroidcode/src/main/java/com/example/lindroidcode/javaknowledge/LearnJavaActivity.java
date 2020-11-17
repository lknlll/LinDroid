package com.example.lindroidcode.javaknowledge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.lindroidcode.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LearnJavaActivity extends AppCompatActivity {

    private static final String TAG = LearnJavaActivity.class.getSimpleName();
    private short sA;
    private short sB;

    private Short sSA;
    private Integer sIA;
    private TextView mTvVarargs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_java);
        sA = (short)1;
        sB = (short)1;
        sSA = 1;
        sIA = 1;
        Log.e(TAG, "onCreate: " + (1 == sA) );
        Log.e(TAG, "onCreate: " + (sA == sB));
        Log.e(TAG, "onCreate: " + (sA == sSA));
        Log.e(TAG, "onCreate: " + (sSA.equals(sIA)));
        Log.e(TAG, "onCreate: " + (sSA.equals(1)));
        Log.e(TAG, "onCreate: " + (sSA.equals((short)1)));

        ArrayList<String> l = new ArrayList<>();
        l.add("aa");
        l.add("bb");
        l.add("cc");
        for (Iterator iter = l.iterator(); iter.hasNext();) {
            String str = (String)iter.next();
            Log.e(TAG, "onCreate: " + str);
        }
        // 迭代器用于while循环，while 括号外 初始化iterator 迭代器
        Iterator iter = l.iterator();
        while(iter.hasNext()){
            String str = (String) iter.next();
            Log.e(TAG, "onCreate: " + str );
        }
        //下面这样会死循环
        /*while(l.iterator().hasNext()){
            String str = l.iterator().next();
            Log.e(TAG, "onCreate: inner iterator" + str );
        }*/

        mTvVarargs =findViewById(R.id.tv_varargs);
        uncertainArgs("“Varargs”是“variable number of arguments”","函数的参数表中有vararg时要放到最后");

        LearnStatic.checkTiming();

        LearnStatic learnStatic = new LearnStatic();
        LearnStatic learnStaticB = new LearnStatic();

        TextView tvRegularExpression = findViewById(R.id.tv_regular_expression);
        tvRegularExpression.append("\n[0-9]+ 表示一位或多位数字，如\"3\"或\"225\"");
        tvRegularExpression.append("\n[0-9]* 表示0位或多位数字，如\"\"或\"1\"或\"22\"");
        tvRegularExpression.append("\n[0-9]? 表示0位或一位数字，如\"\"或\"7\"");

        Log.e(TAG, "onCreate: 一位" + "3".matches("[0-9]+"));
        Log.e(TAG, "onCreate: 多位" + "225".matches("[0-9]+"));
        Log.e(TAG, "onCreate: 0位" + "".matches("[0-9]+"));

        Log.e(TAG, "onCreate: 一位" + "3".matches("[0-9]*"));
        Log.e(TAG, "onCreate: 多位" + "225".matches("[0-9]*"));
        Log.e(TAG, "onCreate: 0位" + "".matches("[0-9]*"));

        Log.e(TAG, "onCreate: 一位" + "3".matches("[0-9]?"));
        Log.e(TAG, "onCreate: 多位" + "225".matches("[0-9]?"));
        Log.e(TAG, "onCreate: 0位" + "".matches("[0-9]?"));
    }

    private void uncertainArgs(String... strings){

        for (int i = 0; i < strings.length; i++) {
            mTvVarargs.append("\n");
            mTvVarargs.append(strings[i]);
        }

    }

}
