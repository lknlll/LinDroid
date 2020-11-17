package com.example.lindroidcode.customchart.barchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.lindroidcode.R;
import com.example.lindroidcode.leastsquares.Line;
import com.example.lindroidcode.leastsquares.LineChartView;

public class BarChartActivity extends AppCompatActivity {

    private FrameLayout V_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        V_text = findViewById(R.id.V_text);

        double [][] arrPoints= {
                {0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0},
                {1.0,-1.0,1.0,1.0,3.0,2.0,-3.0,-2.0,-1.0,2.0,3.0}
        };

        V_text.addView(new BarChartView(this, arrPoints));
    }

}
