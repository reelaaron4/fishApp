package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class sortList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);
        Spinner speciesSpinner = findViewById(R.id.speciesSpinner);
        Spinner lengthSpinner = findViewById(R.id.lengthSpinner);
        Spinner miscSpinner = findViewById(R.id.miscSpinner);
        EditText startDateEditText = findViewById(R.id.startDate);
        EditText endDateEditText = findViewById(R.id.endDate);
        Button sortButton = findViewById(R.id.sortButton);
        TextView error = findViewById(R.id.sortErrorTextView);
        int currId = view_task.getCurrentId();

        ArrayList<String> speciesList = findSpecies(taskList.get(currId).getFish());
        ArrayAdapter<String> speciesAdapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, speciesList);
        speciesSpinner.setAdapter(speciesAdapter);

        String[] lengthList = {"No Selection", "Ascending", "Descending"};
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, lengthList);
        lengthSpinner.setAdapter(lengthAdapter);

        ArrayList<String> miscList = findMisc(taskList.get(currId).getFish());
        ArrayAdapter<String> miscAdapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, miscList);
        miscSpinner.setAdapter(miscAdapter);

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
                SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
                String start = startDateEditText.getText().toString();
                String end = endDateEditText.getText().toString();
                String datePattern = "\\d{2}/\\d{4}";

                if (!start.isEmpty() || !end.isEmpty()) {
                    if (start.matches(datePattern) && end.matches(datePattern)) {
                        int startMonthTemp = Integer.parseInt(start.substring(0, 2));
                        int startYearTemp = Integer.parseInt(start.substring(3));
                        int endMonthTemp = Integer.parseInt(end.substring(0, 2));
                        int endYearTemp = Integer.parseInt(end.substring(3));

                        if (startMonthTemp >= 1 && startMonthTemp <= 12 && endMonthTemp >= 1 && endMonthTemp <= 12) {
                            if (startYearTemp > endYearTemp) {
                                error.setText("Please enter a valid date range.");
                                return;
                            }else if (startYearTemp == endYearTemp) {
                                if (startMonthTemp > endMonthTemp) {
                                    error.setText("Please enter a valid date range.");
                                    return;
                                }
                            }
                            view_fish.setStartDate(startMonthTemp + "/" + startYearTemp);
                            view_fish.setEndDate(endMonthTemp + "/" + endYearTemp);
                        }else{
                            error.setText("Please enter a valid month (1-12).");
                            return;
                        }
                    }else{
                        error.setText("Please enter a valid date.");
                        return;
                    }
                }

                if (start.isEmpty() && end.isEmpty()) {
                    view_fish.setStartDate("01/1800");
                    view_fish.setEndDate(dateFormatMonth.format(calendar.getTime()) + "/" + dateFormatYear.format(calendar.getTime()));
                }
                Intent switchToView = new Intent(getApplicationContext(), view_fish.class);
                startActivity(switchToView);
            }
        });
        speciesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                view_fish.setSpeciesSort(speciesSpinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        lengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                view_fish.setLengthSort(lengthSpinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        miscSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                view_fish.setMiscSort(miscSpinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private ArrayList<String> findSpecies(ArrayList<Fish> list) {
        ArrayList<String> species = new ArrayList<>();
        species.add("No Selection");
        for (int i = 0; i < list.size(); i++) {
            if (!species.contains(list.get(i).getSpecies())) {
                species.add(list.get(i).getSpecies());
            }
        }
        return species;
    }
    private ArrayList<String> findMisc(ArrayList<Fish> list) {
        ArrayList<String> misc = new ArrayList<>();
        misc.add("No Selection");
        for (int i = 0; i < list.size(); i++) {
            if (!misc.contains(list.get(i).getMisc())) {
                misc.add(list.get(i).getMisc());
            }
            if(!misc.contains(list.get(i).getMisc2())){
                misc.add(list.get(i).getMisc2());
            }
        }
        for(int i = 0; i < misc.size(); i++){
            if(misc.get(i).equals("")){
                misc.remove(i);
            }
        }
        return misc;
    }
}
