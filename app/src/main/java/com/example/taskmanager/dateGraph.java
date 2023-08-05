package com.example.taskmanager;

import android.content.Intent;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class dateGraph extends AppCompatActivity {
    ArrayList<BarEntry> barEntries;
    private int dateRangeSelection = 2;
    private int[] dateRange = {12, 3, 1, 3, 30};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_graph);

        dateRangeSelection = graph.getDateRangeSelection();

        BarChart barChart = findViewById(R.id.barchart);
        Button increaseRange = findViewById(R.id.buttonBig);
        Button decreaseRange = findViewById(R.id.buttonSmall);
        Button back = findViewById(R.id.buttonBack);
        Button forward = findViewById(R.id.buttonForward);
        Button exit = findViewById(R.id.buttonExit);

        back.setBackgroundColor(Color.TRANSPARENT);
        forward.setBackgroundColor(Color.TRANSPARENT);

        getData();
        BarDataSet barDataSet = new BarDataSet(barEntries, "");
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
        barChart.setVisibleXRangeMaximum(10);


        increaseRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateRangeSelection++;
                if (dateRangeSelection > 4) {
                    dateRangeSelection = 4;
                }
                graph.setDateRangeSelection(dateRangeSelection);
                Intent switchToDateGraph = new Intent(getApplicationContext(), dateGraph.class);
                startActivity(switchToDateGraph);
            }
        });
        decreaseRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateRangeSelection--;
                if (dateRangeSelection < 0) {
                    dateRangeSelection = 0;
                }
                graph.setDateRangeSelection(dateRangeSelection);
                Intent switchToDateGraph = new Intent(getApplicationContext(), dateGraph.class);
                startActivity(switchToDateGraph);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barChart.moveViewToX((barChart.getLowestVisibleX() - 1));
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barChart.moveViewToX((barChart.getLowestVisibleX() + 1));
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graph.setDateRangeSelection(2);
                Intent switchToGraph = new Intent(getApplicationContext(), graph.class);
                startActivity(switchToGraph);
            }
        });
        back.performClick();
    }

    private void getData() {
        BarChart barChart = findViewById(R.id.barchart);
        barEntries = new ArrayList<>();
        barEntries.clear();
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
        }else if(dateRangeSelection == 3) {
            slots *= dateRange[dateRangeSelection];
            slots += 4;
        }else{
            slots = findDaysBetween(startMonth, startYear, endMonth, endYear);
        }

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
                }else if (dateRangeSelection == 3) {
                    groupIndex *= dateRange[dateRangeSelection];
                    int week = (day - 1) / 10;
                    switch (week) {
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
                    groupIndex = findDaysBetween(startMonth, startYear, month, year, day);
                }
                if(groupIndex >= 0 && groupIndex < slots){
                    dateCounts[groupIndex]++;
                }
            }
        }
        //X axis labels
        //region
        ArrayList<String> xLabels = new ArrayList<>();
        int monthCount = startMonth;
        System.out.println("BEFORE: " + monthCount);
        int yearCount = startYear;
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
                        label = yearCount % 100 + "";
                        yearCount++;
                        xLabels.add(label);
                        break;
                    case 1:
                        label = monthCount + "/" + yearCount % 100;
                        if(monthCount + 3 < 13){
                            label += " - " + (monthCount + 3) + "/" + yearCount % 100;
                        }else{
                            label += " - " + (monthCount + 3 - 12) + "/" + ((yearCount + 1) % 100);
                            monthCount -= 12;
                            yearCount++;
                        }
                        monthCount += 3;
                        xLabels.add(label);
                        break;
                    case 2:
                        label = monthCount + "/" + yearCount % 100;
                        monthCount++;
                        if (monthCount > 12) {
                            monthCount = 1;
                            yearCount++;
                        }
                        xLabels.add(label);
                        break;
                    case 3:
                        label = monthCount + "/" + weekCountStart + "/" + yearCount % 100;
                        weekCountStart += 10;
                        if (weekCountStart > 21) {
                            weekCountStart = 1;
                        }
                        label += " - " + monthCount + "/" + weekCountEnd + "/" + yearCount % 100;
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
                        label = monthCount + "/" + dayCount + "/" + yearCount % 100;
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
        //endregion
        ArrayList<Integer> sortedBarValues = new ArrayList<>();
        ArrayList<String> sortedLabels = new ArrayList<>();
        sortedBarValues.clear();
        sortedLabels.clear();
        for(int i = 0; i < dateCounts.length; i++) {
            if (dateCounts[i] > 0) {
                sortedBarValues.add(dateCounts[i]);
                sortedLabels.add(xLabels.get(i + 1));
            }
        }
        for (int i = 0; i < sortedBarValues.size(); i++) {
            barEntries.add(new BarEntry(i + 0.5f, sortedBarValues.get(i)));
        }
        BarChart barChart = findViewById(R.id.barchart);
        XAxis xAxis = barChart.getXAxis();
        YAxis yAxis = barChart.getAxisLeft();
        YAxis yAxis2 = barChart.getAxisRight();
        xAxis.setValueFormatter(new customXAxisLabelFormatter(sortedLabels));
        //xAxis.setValueFormatter(new IndexAxisValueFormatter(sortedLabels));
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(8f);
        xAxis.setLabelRotationAngle(30f);
        //xAxis.setLabelCount(xLabels.size()-1);
        if(barEntries.size() < 10){
            xAxis.setLabelCount(barEntries.size());
        }else{
            xAxis.setLabelCount(10);
        }
        xAxis.setAxisMinimum(0f);
        yAxis.setAxisMinimum(0f);
        yAxis2.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) (sortedLabels.size()));
        xAxis.setCenterAxisLabels(true);

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
    private int findDaysBetween(int startMonth, int startYear, int endMonth, int endYear){
        if (startMonth == endMonth && startYear == endYear) {
            return 1; // Return 1 to account for the single day
        }
        int lastDay = 0;
        LocalDate startDate = LocalDate.of(startYear, startMonth, 1);
        if(endMonth == 1 || endMonth == 3 || endMonth == 5 || endMonth == 7 || endMonth == 8 || endMonth == 10 || endMonth == 12){
            lastDay = 31;
        }else if(endMonth == 4 || endMonth == 6 || endMonth == 9 || endMonth == 11){
            lastDay = 30;
        }else{
            if(endYear % 4 == 0 && endYear % 100 != 0 || endYear % 400 == 0){
                lastDay = 29;
            }else{
                lastDay = 28;
            }
        }
        LocalDate endDate = LocalDate.of(endYear, endMonth, lastDay);
        int daysBetween = (int) ChronoUnit.DAYS.between(startDate, endDate);
        return daysBetween;
    }
    private int findDaysBetween(int startMonth, int startYear, int endMonth, int endYear, int endDay){
        LocalDate startDate = LocalDate.of(startYear, startMonth, 1);
        LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);
        int daysBetween = (int) ChronoUnit.DAYS.between(startDate, endDate);
        return daysBetween;
    }
}