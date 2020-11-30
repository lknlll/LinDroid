package com.example.lindroidcode.inputstream;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.lindroidcode.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputOutputStreamActivity extends AppCompatActivity {

    private static final String TAG = InputOutputStreamActivity.class.getSimpleName();
    private String mString = "西部球队各队队史得分王已经介绍完毕，今天给大家带来东部季后赛区8支球队队史得分王的介绍。\n" +
            "\n" +
            "密尔沃基雄鹿：贾巴尔\n" +
            "\n" +
            "湖人巨星天勾贾巴尔NBA生涯起步于雄鹿，1969年贾巴尔被雄鹿选为状元，职业生涯共为雄鹿打了6个赛季，为雄鹿出战的467场比赛贾巴尔场均能够拿下30.4分效率极高，而排在贾巴尔之后的是格伦-罗宾逊和蒙克里夫，进入新千年雄鹿的当家球星是迈克尔-里德，在雄鹿的11个赛季里德拿下11554分位列队史第四位，目前雄鹿的头号球星字母哥拿下10435分排在队史第7位，而字母哥的合同将于明年夏天到期，只要和雄鹿完成续约，字母哥超越贾巴尔指日可待。\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_output_stream);
        TextView tvMess = findViewById(R.id.tv_mess);
        TextView tvNormal = findViewById(R.id.tv_correct);

        InputStream is = new ByteArrayInputStream(mString.getBytes());
        byte[] bytes = new byte[12];
        int len;
        try {
            while ((len = is.read(bytes)) != -1 ){
                String str = new String(bytes,0, len);
                //一个汉字是2-4字节，而空格之类的是半个字节，缓冲区是12字节，读到了半个汉字,就会出现乱码
                tvMess.append(str);
                Log.e(TAG, "onCreate: str" + str);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        InputStream isB = new ByteArrayInputStream(mString.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(isB);
        char[] chars = new char[12];
        int lenB;
        try {
            while ((lenB = inputStreamReader.read(chars)) != -1 ){
                String str = new String(chars,0, lenB);
                //将字节输入输出流转换成字符输入输出流
                //InputStreamReader（InputStream in）【字节输入转换流】
                //OutputStreamWriter（OutputStream out）【字节数出转换流】
                //作用：解决文件读取时出现的中文乱码问题
                tvNormal.append(str);
                Log.e(TAG, "onCreate: str" + str);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
