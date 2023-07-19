package com.example.taskmanager;

import static com.example.taskmanager.graph.endMonth;
import static com.example.taskmanager.graph.endYear;
import static com.example.taskmanager.graph.startMonth;
import static com.example.taskmanager.graph.startYear;
import static com.example.taskmanager.view_task.taskList;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class viewTable extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_table);

        TableLayout table = findViewById(R.id.tableLayout);

        int currId = view_task.getCurrentId();
        int x = 0;
        String[] titles = {"Species", "Number of Fish", "Avg Length", "Size Range"};
        ArrayList<String> speciesArray = findSpecies(taskList.get(currId).getFish());
        int columnCount = 4;
        int rowCount = speciesArray.size() + 1;
        String species = graph.speciesSelected;

        if(graph.speciesSelected == null || graph.speciesSelected == "No Selection"){
            for (int i = 0; i < rowCount; i++) {
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                for (int j = 0; j < columnCount; j++) {
                    if (i == 0) {
                        TextView column = new TextView(this);
                        column.setText(titles[j]);
                        column.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                        column.setTextSize(18);
                        row.addView(column);
                    } else {
                        //use switch!!!!!!!!!!!!!!!!
                        if (j == 0) {//fills the rows with the species
                            TextView column = new TextView(this);
                            column.setText(speciesArray.get(i - 1));
                            column.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            column.setTextSize(18);
                            row.addView(column);
                        } else if (j == 1) {//counts the number of each species
                            TextView column = new TextView(this);
                            column.setText(String.valueOf(numFish(taskList.get(currId).getFish(), speciesArray.get(i - 1))));
                            column.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            column.setTextSize(18);
                            row.addView(column);
                        } else if (j == 2) {//finds average length of the species
                            TextView column = new TextView(this);
                            column.setText(String.valueOf(avgLength(taskList.get(currId).getFish(), speciesArray.get(i - 1))));
                            column.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            column.setTextSize(18);
                            row.addView(column);
                        } else if (j == 3) {//finds the size range of the species
                            TextView column = new TextView(this);
                            column.setText(String.valueOf(findMin(taskList.get(currId).getFish(), speciesArray.get(i - 1))) + " - " + String.valueOf(findMax(taskList.get(currId).getFish(), speciesArray.get(i - 1))));
                            column.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            column.setTextSize(18);
                            row.addView(column);
                        }
                    }
                }
                table.addView(row);
            }
        }else{
            int rowCount2 = taskList.size() + 1;
            String[] titlesSpecies = {species, "Number of Fish", "Avg Length", "Size Range"};
            for (int i = 0; i < rowCount2; i++) {
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                for (int j = 0; j < columnCount; j++) {
                    if (i == 0) {
                        TextView column = new TextView(this);
                        column.setText(titlesSpecies[j]);
                        column.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                        row.addView(column);
                    } else {
                        //use switch!!!!!!!!!!!!!!!!
                        if (j == 0) {//fills the rows with the species
                            TextView column = new TextView(this);
                            column.setText(taskList.get(i -1).getName());
                            column.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            row.addView(column);
                        } else if (j == 1) {//counts the number of each species
                            TextView column = new TextView(this);
                            column.setText(String.valueOf(numFish(taskList.get(i - 1).getFish(), species)));
                            column.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            row.addView(column);
                        } else if (j == 2) {//finds average length of the species
                            TextView column = new TextView(this);
                            column.setText(String.valueOf(avgLength(taskList.get(i-1).getFish(), species)));
                            column.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            // column.setBackgroundColor(getResources().getColor(R.color.purple_200));
                            row.addView(column);
                        } else if (j == 3) {//finds the size range of the species
                            TextView column = new TextView(this);
                            column.setText(String.valueOf(findMin(taskList.get(i-1).getFish(), species)) + " - " + String.valueOf(findMax(taskList.get(i-1).getFish(), species)));
                            column.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            row.addView(column);
                        }
                    }
                }
                table.addView(row);
            }
        }
    }

    private ArrayList<String> findSpecies(ArrayList<Fish> list) {
        ArrayList<String> species = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (isWithinDateRange(list.get(i).getDate())) {
                if (!species.contains(list.get(i).getSpecies())) {
                    species.add(list.get(i).getSpecies());
                }
            }
        }
        return species;
    }

    private int numFish(ArrayList<Fish> fishList, String species) {
        int numFish = 0;
        for (int i = 0; i < fishList.size(); i++) {
            if (isWithinDateRange(fishList.get(i).getDate()) && fishList.get(i).getSpecies().equals(species)) {
                numFish++;
            }
        }
        return numFish;
    }

    private double avgLength(ArrayList<Fish> fishList, String species) {
        double totalLength = 0;
        int numFish = 0;
        for (int i = 0; i < fishList.size(); i++) {
            if (isWithinDateRange(fishList.get(i).getDate()) && fishList.get(i).getSpecies().equals(species)) {
                totalLength += fishList.get(i).getLength();
                numFish++;
            }
        }
        return totalLength / numFish;
    }

    private double findMin(ArrayList<Fish> fishList, String species) {
        double min = 0;
        for (int i = 0; i < fishList.size(); i++) {
            if (isWithinDateRange(fishList.get(i).getDate()) && fishList.get(i).getSpecies().equals(species)) {
                if (fishList.get(i).getLength() < min || min == 0) {
                    min = fishList.get(i).getLength();
                }
            }
        }
        return min;
    }

    private double findMax(ArrayList<Fish> fishList, String species) {
        double max = 0;
        for (int i = 0; i < fishList.size(); i++) {
            if (isWithinDateRange(fishList.get(i).getDate()) && fishList.get(i).getSpecies().equals(species)) {
                if (fishList.get(i).getLength() > max || max == 0) {
                    max = fishList.get(i).getLength();
                }
            }
        }
        return max;
    }
    private boolean isWithinDateRange(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return (year > startYear && year < endYear) ||
                (year == startYear && month >= startMonth) ||
                (year == endYear && month <= endMonth);
    }
}
    


