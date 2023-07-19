package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class scatterGraph extends AppCompatActivity {
    int currId = view_task.getCurrentId();
    List<Entry> entries = new ArrayList<>();
    List<Entry> entries1 = new ArrayList<>();
    List<Entry> entries2 = new ArrayList<>();
    List<Entry> entries3 = new ArrayList<>();
    List<Entry> entries4 = new ArrayList<>();
    List<Entry> entries5 = new ArrayList<>();
    List<Entry> entries6 = new ArrayList<>();
    List<Entry> entries7 = new ArrayList<>();
    List<Entry> entries8 = new ArrayList<>();
    List<Entry> entries9 = new ArrayList<>();
    List<Entry> entries10 = new ArrayList<>();

    static ArrayList<String> xAxisLabels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_graph);
        ScatterChart scatterChart = findViewById(R.id.scatter);
        xAxisLabels.clear();


        // Generate 100 random entries with decimal values between 1.0 and 5.0 for both x and y values
      //  List<Entry> entries = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            float xValue = random.nextFloat() * 29 + 1;
            float yValue = random.nextFloat() * 29 + 1;
            Entry entry = new Entry(xValue, yValue);
            entries.add(entry);
            System.out.println("(" + xValue + ", " + yValue + ")");
        }

        //<editor-fold desc="Add elements">
        entries.add(new Entry(8, 5));
        entries.add(new Entry(1, 2));
        entries.add(new Entry(9, 5));
        entries.add(new Entry(4, 1));
        entries.add(new Entry(3, 9));
        entries.add(new Entry(1, 1));
        entries.add(new Entry(2, 7));
        entries.add(new Entry(5, 2));
        entries.add(new Entry(9, 5));
        entries.add(new Entry(1, 6));
        entries.add(new Entry(4, 9));
        entries.add(new Entry(9, 7));
        entries.add(new Entry(8, 5));
        entries.add(new Entry(1, 7));
        entries.add(new Entry(9, 3));
        entries.add(new Entry(7, 3));
        entries.add(new Entry(7, 2));
        entries.add(new Entry(8, 6));
        entries.add(new Entry(6, 6));
        entries.add(new Entry(1, 3));
        entries.add(new Entry(5, 9));
        entries.add(new Entry(5, 5));
        entries.add(new Entry(2, 6));
        entries.add(new Entry(4, 7));
        entries.add(new Entry(5, 2));
        entries.add(new Entry(5, 6));
        entries.add(new Entry(5, 5));
        entries.add(new Entry(9, 3));
        entries.add(new Entry(4, 7));
        entries.add(new Entry(6, 3));
        entries.add(new Entry(4, 9));
        entries.add(new Entry(8, 6));
        entries.add(new Entry(8, 1));
        entries.add(new Entry(1, 3));
        entries.add(new Entry(5, 4));
        entries.add(new Entry(2, 7));
        entries.add(new Entry(7, 4));
        entries.add(new Entry(8, 5));
        entries.add(new Entry(7, 5));
        entries.add(new Entry(6, 8));
        entries.add(new Entry(9, 1));
        entries.add(new Entry(6, 4));
        entries.add(new Entry(7, 4));
        entries.add(new Entry(9, 1));
        entries.add(new Entry(6, 4));
        entries.add(new Entry(1, 6));
        entries.add(new Entry(2, 1));
        entries.add(new Entry(5, 2));
        entries.add(new Entry(3, 5));
        entries.add(new Entry(9, 6));
        entries.add(new Entry(5, 2));
        entries.add(new Entry(7, 5));
        entries.add(new Entry(7, 8));
        entries.add(new Entry(7, 6));
        entries.add(new Entry(9, 1));
        entries.add(new Entry(1, 7));
        entries.add(new Entry(3, 9));
        entries.add(new Entry(6, 6));
        entries.add(new Entry(1, 2));
        entries.add(new Entry(7, 7));
        entries.add(new Entry(4, 6));
        entries.add(new Entry(9, 7));
        entries.add(new Entry(2, 7));
        entries.add(new Entry(9, 5));
        entries.add(new Entry(5, 2));
        entries.add(new Entry(6, 8));
        entries.add(new Entry(5, 2));
        entries.add(new Entry(7, 4));
        entries.add(new Entry(8, 1));
        entries.add(new Entry(2, 6));
        entries.add(new Entry(7, 2));
        entries.add(new Entry(5, 5));
        entries.add(new Entry(5, 6));

       // getData();
        /*XAxis xaxis = scatterChart.getXAxis();
        xaxis.setLabelCount(xAxisLabels.size(), true);
        xaxis.setAxisMaximum((float) (xAxisLabels.size() - 1));
        //MAYBE ADD CUSTOM VALUE FORMATTER TO MATCH LABEL POSITION WITH THE VALUE
        xaxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));*/
        countOccurrences(entries);
        //</editor-fold>

        //<editor-fold desc="Create Dataset">
        ScatterDataSet scatterDataSet = new ScatterDataSet(entries, "");
        ScatterDataSet scatterDataSet1 = new ScatterDataSet(entries1, "1");
        ScatterDataSet scatterDataSet2 = new ScatterDataSet(entries2, "2");
        ScatterDataSet scatterDataSet3 = new ScatterDataSet(entries3, "3");
        ScatterDataSet scatterDataSet4 = new ScatterDataSet(entries4, "4");
        ScatterDataSet scatterDataSet5 = new ScatterDataSet(entries5, "5");
        ScatterDataSet scatterDataSet6 = new ScatterDataSet(entries6, "6");
        ScatterDataSet scatterDataSet7 = new ScatterDataSet(entries7, "7");
        ScatterDataSet scatterDataSet8 = new ScatterDataSet(entries8, "8");
        ScatterDataSet scatterDataSet9 = new ScatterDataSet(entries9, "9");
        ScatterDataSet scatterDataSet10 = new ScatterDataSet(entries10, "10+");

        List<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(scatterDataSet);
        dataSets.add(scatterDataSet1);
        dataSets.add(scatterDataSet2);
        dataSets.add(scatterDataSet3);
        dataSets.add(scatterDataSet4);
        dataSets.add(scatterDataSet5);
        dataSets.add(scatterDataSet6);
        dataSets.add(scatterDataSet7);
        dataSets.add(scatterDataSet8);
        dataSets.add(scatterDataSet9);
        dataSets.add(scatterDataSet10);

        calcTrendline(scatterDataSet, dataSets);

        ScatterData scatterData = new ScatterData(dataSets);
        scatterChart.setData(scatterData);
        //</editor-fold>

        //<editor-fold desc="Set shape and size">
        scatterDataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet.setColor(Color.argb(0, 128, 128, 128));
        scatterDataSet.setScatterShapeSize(0f);
        scatterDataSet1.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet1.setColor(Color.argb(255, 153, 0, 204));
        scatterDataSet1.setScatterShapeSize(25f);
        scatterDataSet2.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet2.setColor(Color.argb(255, 102, 0, 255));
        scatterDataSet2.setScatterShapeSize(30f);
        scatterDataSet3.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet3.setColor(Color.argb(255, 0, 51, 204));
        scatterDataSet3.setScatterShapeSize(35f);
        scatterDataSet4.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet4.setColor(Color.argb(255, 0, 102, 153));
        scatterDataSet4.setScatterShapeSize(40f);
        scatterDataSet5.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet5.setColor(Color.argb(255, 0, 204, 153));
        scatterDataSet5.setScatterShapeSize(45f);
        scatterDataSet6.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet6.setColor(Color.argb(255, 0, 204, 0));
        scatterDataSet6.setScatterShapeSize(50f);
        scatterDataSet7.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet7.setColor(Color.argb(255, 102, 255, 51));
        scatterDataSet7.setScatterShapeSize(55f);
        scatterDataSet8.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet8.setColor(Color.argb(255, 255, 255, 0));
        scatterDataSet8.setScatterShapeSize(60f);
        scatterDataSet9.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet9.setColor(Color.argb(255, 255, 153, 0));
        scatterDataSet9.setScatterShapeSize(65f);
        scatterDataSet10.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet10.setColor(Color.argb(255, 255, 0, 0));
        scatterDataSet10.setScatterShapeSize(70f);

        scatterChart.setBackgroundColor(Color.argb(128, 128, 128, 128));
        XAxis xAxis = scatterChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisMinimum(0f);
        scatterChart.getXAxis().setTextSize(16f);
        scatterChart.getAxisLeft().setTextSize(16f);
        scatterChart.getAxisRight().setTextSize(16f);
        YAxis yAxis = scatterChart.getAxisLeft();
        yAxis.setDrawGridLines(true);
        yAxis.setAxisMinimum(0f);
        scatterChart.getAxisRight().setAxisMinimum(0f);
        Description desc = new Description();
        desc.setText("");
        scatterChart.setDescription(desc);
        System.out.println("passed5");

        entries = null;
        entries1 = null;
        entries2 = null;
        entries3 = null;
        entries4 = null;
        entries5 = null;
        entries6 = null;
        entries7 = null;
        entries8 = null;
        entries9 = null;
        entries10 = null;
//</editor-fold>
    }

    private void getData() {
        String xValues = graph.getType();
        String yValues = graph.getTypeY();

        String typeSpecies = graph.getTypeSpecies();
        ArrayList<Fish> filteredFishList = new ArrayList<>();
        ArrayList<Fish> fishList = (ArrayList<Fish>) taskList.get(currId).getFish();

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
        for(int i = 0; i < filteredFishList.size(); i++){
            System.out.println("def" + filteredFishList.get(i).getLength());
        }
        
        double[] xlengths = new double[filteredFishList.size()];
        double[] xweights = new double[filteredFishList.size()];
        String[] xbaits1 = new String[filteredFishList.size()];
        int[] xbaits = new int[filteredFishList.size()];
        String[] xspecies1 = new String[filteredFishList.size()];
        int[] xspecies = new int[filteredFishList.size()];
        Date[] xdates = new Date[filteredFishList.size()];
        double[] ylengths = new double[filteredFishList.size()];
        double[] yweights = new double[filteredFishList.size()];
        String[] ybaits1 = new String[filteredFishList.size()];
        int[] ybaits = new int[filteredFishList.size()];
        String[] yspecies1 = new String[filteredFishList.size()];
        int[] yspecies = new int[filteredFishList.size()];

        int xarray = 0;
        int yarray = 0;
        switch (xValues) {
            case "length":
                for (int i = 0; i < filteredFishList.size(); i++) {
                    xlengths[i] = filteredFishList.get(i).getLength();
                }
                xarray = 1;
                break;
            case "weight":
                for (int i = 0; i < filteredFishList.size(); i++) {
                    xweights[i] = filteredFishList.get(i).getWeight();
                }
                xarray = 2;
                break;
            case "bait":
                for (int i = 0; i < filteredFishList.size(); i++) {
                    xbaits1[i] = filteredFishList.get(i).getBait();
                    System.out.println("before getString " + xbaits1[i]);
                }
                xbaits = getStringValues(xbaits1);
                xarray = 3;
                break;
            case "species":
                for (int i = 0; i < filteredFishList.size(); i++) {
                    xspecies1[i] = filteredFishList.get(i).getSpecies();
                }
                xspecies = getStringValues(xspecies1);
                xarray = 4;
                break;
            case "date":
                for (int i = 0; i < filteredFishList.size(); i++) {
                    xdates[i] = filteredFishList.get(i).getDate();
                }
                xarray = 5;
                break;
            default:
                break;
        }
        switch (yValues) {
            case "length":
                for (int i = 0; i < filteredFishList.size(); i++) {
                    ylengths[i] = filteredFishList.get(i).getLength();
                }
                yarray = 1;
                break;
            case "weight":
                for (int i = 0; i < filteredFishList.size(); i++) {
                    yweights[i] = filteredFishList.get(i).getWeight();
                }
                yarray = 2;
                break;
            case "bait":
                for (int i = 0; i < filteredFishList.size(); i++) {
                    ybaits1[i] = filteredFishList.get(i).getBait();
                }
                ybaits = getStringValues(ybaits1);
                yarray = 3;
                break;
            case "species":
                for (int i = 0; i < filteredFishList.size(); i++) {
                    yspecies1[i] = filteredFishList.get(i).getSpecies();
                }
                yspecies = getStringValues(yspecies1);
                yarray = 4;
                break;
            default:
                break;
        }
        for (int i = 0; i < filteredFishList.size(); i++) {
            switch (xarray) {
                case 1:
                    switch (yarray) {
                        case 1:
                            entries.add(new Entry((float) xlengths[i], (float) ylengths[i]));
                            break;
                        case 2:
                            entries.add(new Entry((float) xlengths[i], (float) yweights[i]));
                            break;
                        case 3:
                            entries.add(new Entry((float) xlengths[i], (float) ybaits[i]));
                            break;
                        case 4:
                            entries.add(new Entry((float) xlengths[i], (float) yspecies[i]));
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    switch (yarray) {
                        case 1:
                            entries.add(new Entry((float) xweights[i], (float) ylengths[i]));
                            break;
                        case 2:
                            entries.add(new Entry((float) xweights[i], (float) yweights[i]));
                            break;
                        case 3:
                            entries.add(new Entry((float) xweights[i], (float) ybaits[i]));
                            break;
                        case 4:
                            entries.add(new Entry((float) xweights[i], (float) yspecies[i]));
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    switch (yarray) {
                        case 1:
                            entries.add(new Entry((float) xbaits[i], (float) ylengths[i]));
                            break;
                        case 2:
                            entries.add(new Entry((float) xbaits[i], (float) yweights[i]));
                            break;
                        case 3:
                            entries.add(new Entry((float) xbaits[i], (float) ybaits[i]));
                            break;
                        case 4:
                            entries.add(new Entry((float) xbaits[i], (float) yspecies[i]));
                            break;
                        default:
                            break;
                    }
                    break;
                case 4:
                    switch (yarray) {
                        case 1:
                            entries.add(new Entry((float) xspecies[i], (float) ylengths[i]));
                            break;
                        case 2:
                            entries.add(new Entry((float) xspecies[i], (float) yweights[i]));
                            break;
                        case 3:
                            entries.add(new Entry((float) xspecies[i], (float) ybaits[i]));
                            break;
                        case 4:
                            entries.add(new Entry((float) xspecies[i], (float) yspecies[i]));
                            break;
                        default:
                            break;
                    }
            }
        }
    }
    public static int[] getStringValues(String[] arr){
        int uniqueCount = 0;
        String[] unique = new String[arr.length];
        unique[uniqueCount] = arr[0];
        xAxisLabels.add("");
        xAxisLabels.add(arr[0]);
        uniqueCount++;
        boolean found = false;
        for(int i = 1; i < arr.length; i++) {
            found = false;
            for(int j = 0; j < uniqueCount; j++){
                if(arr[i].equals(unique[j])){
                    found = true;
                    break;
                }
            }
            if(!found){
                unique[uniqueCount] = arr[i];
                xAxisLabels.add(arr[i]);
                uniqueCount++;
            }
        }
        int[] result = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < uniqueCount; j++){
                if(arr[i].equals(unique[j])){
                    result[i] = j + 1;
                    break;
                }
            }
        }
        //print out unique array
        xAxisLabels.add("");
        for(int i = 0; i < xAxisLabels.size(); i++){
            System.out.println(i + ": " + xAxisLabels.get(i));
        }
        return result;
    }

    public void countOccurrences(List<Entry> entries) {
        Map<Entry, Integer> occurrencesMap = new HashMap<>();

        // Count occurrences of each entry
        for (Entry entry : entries) {
            boolean found = false;
            for (Entry key : occurrencesMap.keySet()) {
                if (Float.compare(key.getX(), entry.getX()) == 0
                        && Float.compare(key.getY(), entry.getY()) == 0) {
                    int count = occurrencesMap.get(key);
                    occurrencesMap.put(key, count + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                occurrencesMap.put(entry, 1);
            }
        }

        // Convert map to 2D array
        float[][] occurrences = new float[occurrencesMap.size()][2];
        int i = 0;
        for (Entry entry : occurrencesMap.keySet()) {
            occurrences[i][0] = entries.indexOf(entry);
            occurrences[i][1] = occurrencesMap.get(entry);
            i++;
        }

        // Handle float values of count
        for (i = 0; i < occurrences.length; i++) {
            float count = occurrences[i][1];
            switch (Math.round(count)) {
                case 1:
                    entries1.add(entries.get((int) occurrences[i][0]));
                    break;
                case 2:
                    entries2.add(entries.get((int) occurrences[i][0]));
                    break;
                case 3:
                    entries3.add(entries.get((int) occurrences[i][0]));
                    break;
                case 4:
                    entries4.add(entries.get((int) occurrences[i][0]));
                    break;
                case 5:
                    entries5.add(entries.get((int) occurrences[i][0]));
                    break;
                case 6:
                    entries6.add(entries.get((int) occurrences[i][0]));
                    break;
                case 7:
                    entries7.add(entries.get((int) occurrences[i][0]));
                    break;
                case 8:
                    entries8.add(entries.get((int) occurrences[i][0]));
                    break;
                case 9:
                    entries9.add(entries.get((int) occurrences[i][0]));
                    break;
                default:
                    entries10.add(entries.get((int) occurrences[i][0]));
                    break;
            }
        }

        // Print the contents of entries2
        for (i = 0; i < entries2.size(); i++) {
            System.out.println(entries2.get(i).getX() + " " + entries2.get(i).getY());
        }

    }
    public void calcTrendline(ScatterDataSet scatterDataSet, List<IScatterDataSet> dataSets){
        // Find the highest x value in your data set
        float maxX = scatterDataSet.getXMax();

        // Calculate the slope and y-intercept of the trendline using linear regression
        float sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        int numPoints = scatterDataSet.getEntryCount();
        for (int i = 0; i < numPoints; i++) {
            Entry entry = scatterDataSet.getEntryForIndex(i);
            float x = entry.getX();
            float y = entry.getY();
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }
        float meanX = sumX / numPoints;
        float meanY = sumY / numPoints;
        float slope = (sumXY - sumX * meanY) / (sumX2 - sumX * meanX);
        float yIntercept = meanY - slope * meanX;

        // Calculate 100 evenly spaced x values between 0 and maxX, and calculate the corresponding y values using the trendline equation
        ArrayList<Entry> lineEntries = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            float x = i / 99f * maxX;
            float y = slope * x + yIntercept;
            lineEntries.add(new Entry(x, y));
        }

        // Create a new ScatterDataSet and set its properties
        ScatterDataSet trendlineDataSet = new ScatterDataSet(lineEntries, "Trendline");
        dataSets.add(trendlineDataSet);

        trendlineDataSet.setScatterShape(ScatterChart.ScatterShape.SQUARE);
        trendlineDataSet.setColor(Color.argb(255, 0, 0, 0));
        trendlineDataSet.setScatterShapeSize(10f);
    }

}