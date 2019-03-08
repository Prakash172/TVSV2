package com.tvs.splashactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalaryGraphActivity extends AppCompatActivity {

    @BindView(R.id.bar_chart)
    BarChart barChart;

    ArrayList<String> salaries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_graph);
        ButterKnife.bind(this);

        if(getIntent() != null) {
            salaries = getIntent().getStringArrayListExtra("salaries");
        }
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setAxisMinimum(-1);
        Description description =  new Description();
        description.setText("Salaries");
        description.setTextColor(Color.RED);
        description.setTextSize(16);
        barChart.setDescription(description);
        barChart.setFitBars(true);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0 ; i < salaries.size(); i++){
            float salary = Float.parseFloat(salaries.get(i));
            entries.add(new BarEntry(i,salary));
        }

        BarDataSet dataSet = new BarDataSet(entries,"Data set");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.8f);

        barChart.setData(barData);
        barChart.setVisibleXRangeMaximum(10f);

    }
}
