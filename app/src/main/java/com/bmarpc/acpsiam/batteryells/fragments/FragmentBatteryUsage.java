package com.bmarpc.acpsiam.batteryells.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bmarpc.acpsiam.batteryells.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class FragmentBatteryUsage extends Fragment {
    PieChart batteryUsageChart;

    public FragmentBatteryUsage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_battery_usage, container, false);

        batteryUsageChart = v.findViewById(R.id.battery_usage_pie_chart);


        // Sample data for battery usage
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(5.0f, "App 1"));
        entries.add(new PieEntry(3.5f, "App 2"));
        // Add more entries as needed


        PieDataSet dataSet = new PieDataSet(entries, "Battery Usage");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        PieData pieData = new PieData(dataSet);
        batteryUsageChart.setData(pieData);

        // Configure chart appearance
        batteryUsageChart.getDescription().setEnabled(false);
        batteryUsageChart.setEntryLabelColor(Color.BLACK);
        batteryUsageChart.setCenterText("Battery Usage");
        batteryUsageChart.setCenterTextSize(18f);
        batteryUsageChart.animate();

        return v;
    }
}