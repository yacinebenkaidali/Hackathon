package com.anychart.sample;

import android.content.res.Resources;


import com.anychart.sample.charts.ColumnChartActivity;

import com.anychart.sample.charts.LineChartActivity;
import com.example.anis.widget_habbit.R;

import java.util.ArrayList;

public class Chart {

    private String name;
    private Class activityClass;

    private Chart(String name, Class activityClass) {
        this.name = name;
        this.activityClass = activityClass;
    }

    public String getName() {
        return name;
    }

    Class getActivityClass() {
        return activityClass;
    }

    static ArrayList<Chart> createChartList(Resources resources) {
        ArrayList<Chart> chartList = new ArrayList<>();


        chartList.add(new Chart(resources.getString(R.string.column_chart), ColumnChartActivity.class));
        chartList.add(new Chart(resources.getString(R.string.line_chart), LineChartActivity.class));

//        chartList.add(new Chart(resources.getString(R.string.gantt_chart), GanttChartActivity.class));

        return chartList;
    }

}
