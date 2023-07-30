package com.example.taskmanager;

import android.graphics.Color;
import android.os.Bundle;

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

public class graphTest<ValueFormatter> extends AppCompatActivity {
    ArrayList<BarEntry> barEntries;
    int rangeMidpoint = 0;
    String barSelection, speciesGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_test);

        barSelection = graph.getBarSelection();

        BarChart barChart = findViewById(R.id.barchart);
        getData();
        BarDataSet barDataSet = new BarDataSet(barEntries, "Fish Data");
        switch(barSelection){
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
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
       // barChart.setScaleEnabled(true);
       // barChart.setPinchZoom(true);
        barChart.setBackgroundColor(Color.argb(128, 128, 128, 128));
        barChart.setGridBackgroundColor(Color.argb(0, 128, 128, 128));
        barChart.setVisibleXRangeMaximum(10);
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
        int range = 0;
        try {
            range = graph.getRange();
        }catch (Exception e){
            range = 5;
        }

        int maxMultiple = (int) Math.ceil(max / (double) range) * range;
        int slots = (int) Math.ceil(maxMultiple / range);
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
        for (int i = -1; i < slots + 1; i++) {
            if (i == -1 || i == slots) {
                xLabels.add("");
            } else {
                int start = i * range + 1;
                int end = Math.min((i + 1) * range, maxMultiple);
                String label = start + "-" + end;
                xLabels.add(label);
            }
        }
        for (int i = 0; i < lengthCounts.length; i++) {
            if (lengthCounts[i] > 0) {
                barEntries.add(new BarEntry(i + 1, lengthCounts[i]));
            }
        }
        XAxis xAxis = barChart.getXAxis();
        YAxis yAxis = barChart.getAxisLeft();
        YAxis yAxis2 = barChart.getAxisRight();
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(8f);
        xAxis.setLabelRotationAngle(30f);
        xAxis.setLabelCount(xLabels.size(), true);
        xAxis.setAxisMinimum(0f);
        yAxis.setAxisMinimum(0f);
        yAxis2.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) (xLabels.size() - 1));
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
    }
    private void calcStrings(String[] arr){
       BarChart barChart = findViewById(R.id.barchart);
       String[][] counts = countOccurrences(arr);
       int slots = counts.length;
       ArrayList<String> xLabels = new ArrayList<>();
        for (int i = -1; i < slots + 1; i++) {
            if (i == -1 || i == slots) {
                xLabels.add("");
            } else {
                String label = counts[i][0];
                xLabels.add(label);
            }
        }
        for (int i = 0; i < counts.length; i++) {
            int count = Integer.parseInt(counts[i][1]);
            if (count > 0) {
                barEntries.add(new BarEntry(i + 1, count));
            }
        }
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setLabelRotationAngle(30f);
        xAxis.setLabelCount(xLabels.size(), true);
        xAxis.setAxisMaximum((float) (xLabels.size() - 1));
        xAxis.setValueFormatter(new customXAxisLabelFormatter(xLabels));
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
}
