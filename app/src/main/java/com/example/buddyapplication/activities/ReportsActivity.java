package com.example.buddyapplication.activities;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buddyapplication.R;
import com.example.buddyapplication.database.DBHelper;
import com.example.buddyapplication.model.Buddy;
import com.example.buddyapplication.utils.SessionManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity {

    private PieChart pieChart;
    private BarChart barChart;
    private DBHelper dbHelper;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        dbHelper = new DBHelper(this);
        sessionManager = new SessionManager(this);
        pieChart = findViewById(R.id.chart_gender);
        barChart = findViewById(R.id.chart_birthday);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        setupGenderChart();
        setupBirthdayChart();
    }

    private void setupGenderChart() {
        List<Buddy> buddyList = dbHelper.getAllBuddies(sessionManager.getUsername());
        int maleCount = 0;
        int femaleCount = 0;

        for (Buddy b : buddyList) {
            if ("Male".equalsIgnoreCase(b.getGender())) {
                maleCount++;
            } else if ("Female".equalsIgnoreCase(b.getGender())) {
                femaleCount++;
            }
        }

        List<PieEntry> entries = new ArrayList<>();
        if (maleCount > 0) entries.add(new PieEntry(maleCount, "Male"));
        if (femaleCount > 0) entries.add(new PieEntry(femaleCount, "Female"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[]{Color.parseColor("#42A5F5"), Color.parseColor("#EC407A")});
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Total: " + buddyList.size());
        pieChart.setCenterTextSize(16f);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void setupBirthdayChart() {
        List<Buddy> buddyList = dbHelper.getAllBuddies(sessionManager.getUsername());
        int[] monthCounts = new int[12];

        for (Buddy b : buddyList) {
            String dob = b.getDob();
            try {
                String[] parts = dob.split("/");
                if (parts.length >= 2) {
                    int month = Integer.parseInt(parts[1]);
                    if (month >= 1 && month <= 12) {
                        monthCounts[month - 1]++;
                    }
                }
            } catch (Exception e) {
            }
        }

        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, monthCounts[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Friends");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate();
    }
}