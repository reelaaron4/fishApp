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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class graphTest<ValueFormatter> extends AppCompatActivity {
    ArrayList<BarEntry> barEntries;
    private int rangeMidpoint = 0;
    private String barSelection, speciesGraph;
    private int dateRangeSelection = 2;
    private int[] dateRange = {12, 3, 1, 3, 30};
    private int range = 1;
    private int maxVisible = 15;
    private int slots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_test);

        range = graph.getRange();
        maxVisible = graph.getMaxVisible();

        barSelection = graph.getBarSelection();
        BarChart barChart = findViewById(R.id.barchart);
        Button increaseRange = findViewById(R.id.buttonBig);
        Button decreaseRange = findViewById(R.id.buttonSmall);
        Button back = findViewById(R.id.buttonBack);
        Button forward = findViewById(R.id.buttonForward);
        Button exit = findViewById(R.id.buttonExit);

        back.setBackgroundColor(Color.TRANSPARENT);
        forward.setBackgroundColor(Color.TRANSPARENT);

        getData();
        BarDataSet barDataSet = new BarDataSet(barEntries, "Fish Data");
        switch (barSelection) {
            case "Length":
                barDataSet = new BarDataSet(barEntries, "Fish Lengths");
                break;
            case "Weight":
                barDataSet = new BarDataSet(barEntries, "Fish Weight");
                break;
            case "Bait":
                barDataSet = new BarDataSet(barEntries, "Baits");
                break;
            case "Species":
                barDataSet = new BarDataSet(barEntries, "Species");
                break;
        }
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
        barChart.setVisibleXRangeMaximum(maxVisible);


        increaseRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maxVisible++;
                maxVisible = (maxVisible > slots) ? slots : maxVisible;
                maxVisible = (maxVisible > 25) ? 25 : maxVisible;
                graph.setMaxVisible(maxVisible);
                Intent switchToGraph = new Intent(getApplicationContext(), graphTest.class);
                startActivity(switchToGraph);
            }
        });
        decreaseRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maxVisible--;
                maxVisible = (maxVisible < 1) ? 1 : maxVisible;
                graph.setMaxVisible(maxVisible);
                Intent switchToGraph = new Intent(getApplicationContext(), graphTest.class);
                startActivity(switchToGraph);

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
                graph.setMaxVisible(15);
                Intent switchToGraph = new Intent(getApplicationContext(), graph.class);
                startActivity(switchToGraph);
            }
        });
        back.performClick();
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

        switch(barSelection){
            case "Length":
                double[] lengths = new double[filteredFishList.size()];
                for (int i = 0; i < filteredFishList.size(); i++) {
                    lengths[i] = filteredFishList.get(i).getLength();
                }
                calcDoubles(lengths);
                barChart.setViewPortOffsets(dpToPx(40), dpToPx(10), dpToPx(40), dpToPx(50));
                break;
            case "Weight":
                double[] weights = new double[filteredFishList.size()];
                for (int i = 0; i < filteredFishList.size(); i++) {
                    weights[i] = filteredFishList.get(i).getWeight();
                }
                calcDoubles(weights);
                barChart.setViewPortOffsets(dpToPx(40), dpToPx(10), dpToPx(40), dpToPx(50));
                break;
            case "Bait":
                String[] baits = new String[filteredFishList.size()];
                for (int i = 0; i < filteredFishList.size(); i++) {
                    baits[i] = filteredFishList.get(i).getBait();
                }
                calcStrings(baits);
                barChart.setViewPortOffsets(dpToPx(40), dpToPx(10), dpToPx(40), dpToPx(70));
                break;
            case "Species":
                String[] species = new String[filteredFishList.size()];
                for (int i = 0; i < filteredFishList.size(); i++) {
                    species[i] = filteredFishList.get(i).getSpecies();
                }
                calcStrings(species);
                barChart.setViewPortOffsets(dpToPx(40), dpToPx(10), dpToPx(40), dpToPx(70));
                break;
        }
    }
    private void calcDoubles(double[] arr){
        BarChart barChart = findViewById(R.id.barchart);
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = (int) Math.ceil(arr[i]);
            }
        }

        int maxMultiple = (int) Math.ceil(max / (double) range) * range;
        slots = (int) Math.ceil(maxMultiple / range);
        if(range % 2 == 0){
            rangeMidpoint = range / 2;
        }else{
            rangeMidpoint = range / 2 + 1;
        }

        int[] lengthCounts = new int[slots];
        for (double length : arr) {
            int groupIndex = (int) (Math.ceil(length) - 1) / range;
            if(length != 0.0){
                lengthCounts[groupIndex]++;
            }
        }

        ArrayList<String> xLabels = new ArrayList<>();
        for (int i = 0; i < slots; i++) {
            int start = i * range + 1;
            int end = Math.min((i + 1) * range, maxMultiple);
            String label = start + "-" + end;
            xLabels.add(label);
        }
        for (int i = 0; i < lengthCounts.length; i++) {
            if (lengthCounts[i] > 0) {
                barEntries.add(new BarEntry(i + 0.5f, lengthCounts[i]));
            }
        }
        XAxis xAxis = barChart.getXAxis();
        YAxis yAxis = barChart.getAxisLeft();
        YAxis yAxis2 = barChart.getAxisRight();
        xAxis.setValueFormatter(new customXAxisLabelFormatter(xLabels));
       // xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(8f);
        xAxis.setLabelRotationAngle(30f);
       // xAxis.setLabelCount(xLabels.size()-1);
        maxVisible = (maxVisible > slots) ? slots : maxVisible;
        if(slots < maxVisible){
            xAxis.setLabelCount(slots);
        }else {
            xAxis.setLabelCount(maxVisible + 1, true);
        }
        xAxis.setAxisMinimum(0f);
        yAxis.setAxisMinimum(0f);
        yAxis2.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) (xLabels.size()));
        xAxis.setCenterAxisLabels(true);
    }
    private void calcStrings(String[] arr){
       BarChart barChart = findViewById(R.id.barchart);
       String[][] counts = countOccurrences(arr);
       slots = counts.length;
       ArrayList<String> xLabels = new ArrayList<>();
        for (int i = 0; i < slots; i++) {
                String label = counts[i][0];
                xLabels.add(label);
        }
        for (int i = 0; i < counts.length; i++) {
            int count = Integer.parseInt(counts[i][1]);
            if (count > 0) {
                barEntries.add(new BarEntry(i + 0.5f, count));
            }
        }
        XAxis xAxis = barChart.getXAxis();
        YAxis yAxis = barChart.getAxisLeft();
        YAxis yAxis2 = barChart.getAxisRight();
        xAxis.setValueFormatter(new customXAxisLabelFormatter(xLabels));
        //xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(8f);
        xAxis.setLabelRotationAngle(30f);
        // xAxis.setLabelCount(xLabels.size()-1);
        if(slots < maxVisible){
            xAxis.setLabelCount(slots);
        }else {
            xAxis.setLabelCount(maxVisible + 1, true);
        }
        xAxis.setAxisMinimum(0f);
        yAxis.setAxisMinimum(0f);
        yAxis2.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) (xLabels.size()));
        xAxis.setCenterAxisLabels(true);
    }
    public static String[][] countOccurrences(String[] arr) {
        // Create a 2D array to store the count of each string
        String[][] countArray = new String[arr.length][2];

        // Initialize the countArray with the first element of the input array
        countArray[0][0] = arr[0];
        countArray[0][1] = "1";

        // Iterate through the rest of the input array and count the occurrences of each string
        int countIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            boolean found = false;
            // Check if the string is already present in the countArray
            for (int j = 0; j <= countIndex; j++) {
                if (countArray[j][0].equals(arr[i])) {
                    // If it is, increment its count
                    int count = Integer.parseInt(countArray[j][1]) + 1;
                    countArray[j][1] = Integer.toString(count);
                    found = true;
                    break;
                }
            }
            // If the string is not already present in the countArray, add it
            if (!found) {
                countIndex++;
                countArray[countIndex][0] = arr[i];
                countArray[countIndex][1] = "1";
            }
        }
        // Convert the countArray to a 2D string array and return it
        String[][] result = new String[countIndex + 1][2];
        for (int i = 0; i <= countIndex; i++) {
            result[i][0] = countArray[i][0];
            result[i][1] = countArray[i][1];
        }
        return result;
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
