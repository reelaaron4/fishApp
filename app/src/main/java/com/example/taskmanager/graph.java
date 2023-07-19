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
import java.util.List;

public class graph extends AppCompatActivity {

    public static String type = "length";
    public static int range = 1;
    public static String typeY = null;
    public static String typeSpecies = null;
    public static int currentId = view_task.getCurrentId();
    public static ArrayList<String> speciesList = new ArrayList<String>();
    public static String speciesSelected = null;
    public static int startMonth;
    public static int startYear;
    public static int endMonth;
    public static int endYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        EditText range = findViewById(R.id.range);
        EditText startDateEditText = findViewById(R.id.startDate);
        EditText endDateEditText = findViewById(R.id.endDate);
        Button createGraph = findViewById(R.id.createGraph);
        Button viewButton = findViewById(R.id.viewButton);
        Button createTable = findViewById(R.id.createTable);
        TextView error = findViewById(R.id.graphErrorTextView);

        Spinner speciesSpinner = findViewById(R.id.speciesSpinner);
        List<String> fishNames = new ArrayList<>(view_task.fishNames);
        fishNames.add(0, "No Selection"); // Add "No Selection" as the first item
        ArrayAdapter<String> speciesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fishNames);
        speciesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speciesSpinner.setAdapter(speciesAdapter);


        String[] xaxisOptions = {"length", "weight", "bait", "species", "date"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, xaxisOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner dropDown = findViewById(R.id.dropDown);
        dropDown.setAdapter(adapter);

        String[] yaxisOptions = {"No Selection","length", "weight", "bait", "species"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yaxisOptions);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner dropDownY = findViewById(R.id.dropDownY);
        dropDownY.setAdapter(adapter2);

        speciesList.clear();
        speciesList.add("No Selection");
        setSpecies(currentId, speciesList);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, speciesList);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner dropDownSpecies = findViewById(R.id.dropDownSpecies);
        dropDownSpecies.setAdapter(adapter3);

        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                type = selectedItem;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = "length";
            }
        });
        dropDownY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                typeY = selectedItem;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeY = null;
            }
        });
        dropDownSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                typeSpecies = selectedItem;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeSpecies = null;
            }
        });
        createGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rangeValue = 5;
                try{
                    rangeValue = Integer.parseInt(range.getText().toString());
                    setRange(rangeValue);
                }catch (NumberFormatException e){
                    error.setText("Please enter a valid range. Defaulting to 5.");
                    setRange(5);
                    return;
                }
                if(typeY == "No Selection" || typeY == null){
                    Intent switchToGraphView = new Intent(getApplicationContext(), graphTest.class);
                    startActivity(switchToGraphView);
                }else{
                    Intent switchToScatter = new Intent(getApplicationContext(), scatterGraph.class);
                    startActivity(switchToScatter);
                }

            }
        });
        viewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToView = new Intent(getApplicationContext(), view_fish.class);
                startActivity(switchToView);

            }
        });
        createTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                            startMonth = startMonthTemp;
                            startYear = startYearTemp;
                            endMonth = endMonthTemp;
                            endYear = endYearTemp;
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
                    startMonth = 1;
                    startYear = 1800;
                    endMonth =  Integer.parseInt(dateFormatMonth.format(calendar.getTime()));
                    endYear =  Integer.parseInt(dateFormatYear.format(calendar.getTime()));
                }
                Intent switchToTable = new Intent(getApplicationContext(), viewTable.class);
                startActivity(switchToTable);

            }
        });
        speciesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                speciesSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void setSpecies(int currentId, ArrayList<String> speciesList){
        speciesList.add(((Fish) taskList.get(currentId).getFish().get(0)).getSpecies());

        for(int i = 0; i < taskList.get(currentId).getFish().size(); i++){
            boolean found = false;
            for(int j = 0; j < speciesList.size(); j++){
                if(((Fish) taskList.get(currentId).getFish().get(i)).getSpecies().equals(speciesList.get(j))){
                    found = true;
                    System.out.println(((Fish) taskList.get(currentId).getFish().get(i)).getSpecies());
                }
            }
            if(!found){
                speciesList.add(((Fish) taskList.get(currentId).getFish().get(i)).getSpecies());
            }
        }

    }
    public void setType(String type){this.type =  type;}
    public void setRange(int range){this.range = range;}
    public static String getType(){return type;}
    public static int getRange(){return range;}
    public static String getTypeY(){return typeY;}
    public static String getTypeSpecies(){return typeSpecies;}
}