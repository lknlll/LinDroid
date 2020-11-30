package com.example.lindroidcode.leastsquares;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.lindroidcode.R;

public class LineChartActivity extends AppCompatActivity {

    private FrameLayout V_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        V_text = findViewById(R.id.V_text);

        double [][] arrPoints= {
                {0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0,11.0,12.0,13.0,14.0},
                {5.0,2.0,18.0,15.0,33.0,29.0,46.0,43.0,61.0,57.0,74.0,72.0,90.0,91.0,98.0}
        };
        double [][] dataSet = Line.deTrend(arrPoints);

        V_text.addView(new LineChartView(this, arrPoints,dataSet));
    }

}
