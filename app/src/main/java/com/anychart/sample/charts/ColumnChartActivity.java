package com.anychart.sample.charts;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.anis.widget_habbit.R;

import java.util.ArrayList;
import java.util.List;

public class ColumnChartActivity extends AppCompatActivity {
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);


        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();

        try {
            db = getApplicationContext().openOrCreateDatabase("Habit_Tracker", Context.MODE_PRIVATE, null);
            Cursor c = db.rawQuery("Select DESCRIPTION,COUNT_NUM from HABIT inner join TIME_HABIT on HABIT.CODE_HABIT=TIME_HABIT.CODE_HABIT", null);
            int DESIndex = c.getColumnIndex("DESCRIPTION");
            int CountIndex = c.getColumnIndex("COUNT_NUM");
            c.moveToFirst();
            if (c != null) {
                do {
                    String des= c.getString(DESIndex);
                    int cont=c.getInt(CountIndex);
                    data.add(new ValueDataEntry(des, cont));
                } while (c.moveToNext());
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("ColumnChart to Habbits in Month");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(1).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Days");
        cartesian.yAxis(0).title("Achivements");

        anyChartView.setChart(cartesian);
    }
}
