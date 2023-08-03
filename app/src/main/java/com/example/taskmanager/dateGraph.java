package com.example.taskmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class dateGraph extends AppCompatActivity {
    ArrayList<BarEntry> barEntries;
    private int rangeMidpoint = 0;
    private String barSelection, speciesGraph;
    private int dateRangeSelection = 2;
    private int[] dateRange = {12, 3, 1, 3, 30};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_graph);

        barSelection = graph.getBarSelection();

        BarChart barChart = findViewById(R.id.barchart);
        Button increaseRange = findViewById(R.id.buttonBig);
        Button decreaseRange = findViewById(R.id.buttonSmall);
        Button back = findViewById(R.id.buttonBack);
        Button forward = findViewById(R.id.buttonForward);

        back.setBackgroundColor(Color.TRANSPARENT);
        forward.setBackgroundColor(Color.TRANSPARENT);

        getData();
        BarDataSet barDataSet = new BarDataSet(barEntries, "Fish Data");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barData.setBarWidth(0.85f);

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setTextSize(16f);
        barChart.getAxisLeft().setTextSize(16f);
        barChart.getAxisRight().setTextSize(16f);
        barChart.getXAxis().setGridLineWidth(1f);
        barChart.getAxisLeft().setGridLineWidth(1f);
        barChart.getDescription().setEnabled(false);

        // Set the padding on the left and right sides of the chart
        barChart.setFitBars(true);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBorders(false);
        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setBackgroundColor(Color.argb(128, 128, 128, 128));
        barChart.setGridBackgroundColor(Color.argb(0, 128, 128, 128));
        barChart.setVisibleXRangeMinimum(1);
        barChart.setVisibleXRangeMaximum(6);

        increaseRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateRangeSelection++;
                if (dateRangeSelection > 4) {
                    dateRangeSelection = 4;
                }
                getData();
                barChart.invalidate();
                barChart.setVisibleXRangeMinimum(1);
                barChart.setVisibleXRangeMaximum(6);
                System.out.println(dateRangeSelection);
            }
        });

        decreaseRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateRangeSelection--;
                if (dateRangeSelection < 0) {
                    dateRangeSelection = 0;
                }
                getData();
                barChart.invalidate();
                barChart.setVisibleXRangeMinimum(1);
                barChart.setVisibleXRangeMaximum(6);
                System.out.println(dateRangeSelection);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barChart.moveViewToX(barChart.getLowestVisibleX() - 1);
                System.out.println("BACK BUTTON");
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barChart.moveViewToX(barChart.getHighestVisibleX() + 1);
                System.out.println("FORWARD BUTTON");
            }
        });
    }

    private void getData() {
        BarChart barChart = findViewById(R.id.barchart);
        barEntries = new ArrayList<>();
        int currId = view_task.getCurrentId();
        String typeSpecies = graph.getSpeciesGraph();

        ArrayList<Fish> filteredFishList = new ArrayList<>();
        ArrayList<Fish> fishList = (ArrayList<Fish>) view_task.taskList.get(currId).getFish();

        // Filter the fishList based on typeSpecies
        if (typeSpecies != null && !typeSpecies.equals("No Selection")) {
            for (Fish fish : fishList) {
                if (fish.getSpecies().equals(typeSpecies)) {
                    filteredFishList.add(fish);
                }
            }
        } else {
            filteredFishList.addAll(fishList);
        }
        Date[] dates = new Date[filteredFishList.size()];
        for (int i = 0; i < filteredFishList.size(); i++) {
            dates[i] = filteredFishList.get(i).getDate();
        }
        calcDate(dates);
        barChart.setViewPortOffsets(dpToPx(40), dpToPx(10), dpToPx(40), dpToPx(70));
    }
    private void calcDate(Date[] arr){
        int startMonth = graph.getStartMonthGraph();
        int endMonth = graph.getEndMonthGraph();
        int startYear = graph.getStartYearGraph();
        int endYear = graph.getEndYearGraph();
        Date[] dates = new Date[arr.length];
        int datesIndex = 0;
        for(int i = 0; i < arr.length; i++){
            if(isWithinDateRange(arr[i])){
                dates[datesIndex] = arr[i];
                datesIndex++;
            }
        }
        //default date range is 1 month
        int slots = (endYear - startYear) * 12 - (startMonth - endMonth);
        if(dateRangeSelection < 3) {
            slots /= dateRange[dateRangeSelection];
            slots++;
        }else{
            slots *= dateRange[dateRangeSelection];
            if(dateRangeSelection == 3){
                slots +=4;
            }else{
                slots+=30;
            }
        }
        System.out.println("slots " + slots);
        int[] dateCounts = new int[slots];
        for (Date date : dates) {
            if(date != null){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                int groupIndex = (year - startYear) * 12 - (startMonth - month);
                if(dateRangeSelection < 3) {
                    groupIndex /= dateRange[dateRangeSelection];
                }else{
                    groupIndex *= dateRange[dateRangeSelection];
                    if(dateRangeSelection == 3) {
                        int week = (day - 1) / 10;
                        switch(week){
                            case 0:
                                groupIndex += 0;
                                break;
                            case 1:
                                groupIndex += 1;
                                break;
                            default:
                                groupIndex += 2;
                                break;
                        }
                    }else{
                        groupIndex += day;
                    }

                }
                if(groupIndex >= 0 && groupIndex < slots){
                    dateCounts[groupIndex]++;
                }
            }
        }
        ArrayList<String> xLabels = new ArrayList<>();
        int monthCount = startMonth;
        int yearCount = startYear % 100;
        int weekCountStart = 1;
        int weekCountEnd = 10;
        int dayCount = 1;
        String label = "";
        for (int i = -1; i < slots + 1; i++) {
            if (i == -1 || i == slots) {
                xLabels.add("");
            } else {
                boolean m31 = monthCount == 1 || monthCount == 3 || monthCount == 5 || monthCount == 7 || monthCount == 8 || monthCount == 10 || monthCount == 12;
                boolean m30 = monthCount == 4 || monthCount == 6 || monthCount == 9 || monthCount == 11;
                boolean leapYear = yearCount % 4 == 0 && yearCount % 100 != 0 || yearCount % 400 == 0;
                switch(dateRangeSelection) {
                    case 0:
                        label = yearCount + "";
                        yearCount++;
                        xLabels.add(label);
                        break;
                    case 1:
                        label = monthCount + "/" + yearCount;
                        if(monthCount + 3 < 13){
                            label += " - " + (monthCount + 3) + "/" + yearCount;
                        }else{
                            label += " - " + (monthCount + 3 - 12) + "/" + (yearCount + 1);
                            yearCount++;
                        }
                        monthCount += 3;
                        xLabels.add(label);
                        break;
                    case 2:
                        label = monthCount + "/" + yearCount;
                        monthCount++;
                        if (monthCount > 12) {
                            monthCount = 1;
                            yearCount++;
                        }
                        xLabels.add(label);
                        break;
                    case 3:
                        label = monthCount + "/" + weekCountStart + "/" + yearCount;
                        weekCountStart += 10;
                        if (weekCountStart > 21) {
                            weekCountStart = 1;
                        }
                        label += " - " + monthCount + "/" + weekCountEnd + "/" + yearCount;
                        if(weekCountStart == 1){
                            monthCount++;
                            if (monthCount > 12) {
                                monthCount = 1;
                                yearCount++;
                            }
                        }
                        weekCountEnd += 10;
                        if (weekCountStart == 21) {
                            if(m31){
                                weekCountEnd = 31;
                            }else if(m30){
                                weekCountEnd = 30;
                            }else{
                                if(leapYear){
                                    weekCountEnd = 29;
                                }else{
                                    weekCountEnd = 28;
                                }
                            }
                        }else if(weekCountStart == 1){
                            weekCountEnd = 10;
                        }
                        xLabels.add(label);
                        break;
                    case 4:
                        label = monthCount + "/" + dayCount + "/" + yearCount;
                        dayCount++;
                        if(m31){
                            if(dayCount > 31){
                                dayCount = 1;
                                monthCount++;
                                if(monthCount > 12){
                                    monthCount = 1;
                                    yearCount++;
                                }
                            }
                        }else if(m30){
                            if(dayCount > 30){
                                dayCount = 1;
                                monthCount++;
                                if(monthCount > 12){
                                    monthCount = 1;
                                    yearCount++;
                                }
                            }
                        }else{
                            if(leapYear){
                                if(dayCount > 29){
                                    dayCount = 1;
                                    monthCount++;
                                    if(monthCount > 12){
                                        monthCount = 1;
                                        yearCount++;
                                    }
                                }
                            }else{
                                if(dayCount > 28){
                                    dayCount = 1;
                                    monthCount++;
                                    if(monthCount > 12){
                                        monthCount = 1;
                                        yearCount++;
                                    }
                                }
                            }
                        }
                        xLabels.add(label);
                        break;
                }
            }
        }
        for (int i = 0; i < dateCounts.length; i++) {
            if (dateCounts[i] > 0) {
                barEntries.add(new BarEntry(i + 1, dateCounts[i]));
            }
        }
        BarChart barChart = findViewById(R.id.barchart);
        XAxis xAxis = barChart.getXAxis();
        YAxis yAxis = barChart.getAxisLeft();
        YAxis yAxis2 = barChart.getAxisRight();
        //xAxis.setValueFormatter(new customXAxisLabelFormatter(xLabels));
         xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(8f);
        xAxis.setLabelRotationAngle(30f);
        //xAxis.setLabelCount(xLabels.size()-1);
        xAxis.setLabelCount(7, true);
        xAxis.setAxisMinimum(0f);
        yAxis.setAxisMinimum(0f);
        yAxis2.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) (xLabels.size()-1));
        xAxis.setCenterAxisLabels(false);


    }
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
    private boolean isWithinDateRange(Date date) {
        int startMonth = graph.getStartMonthGraph();
        int endMonth = graph.getEndMonthGraph();
        int startYear = graph.getStartYearGraph();
        int endYear = graph.getEndYearGraph();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return (year > startYear && year < endYear) ||
                (year == startYear && month >= startMonth) ||
                (year == endYear && month <= endMonth);
    }
}