package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class graph extends AppCompatActivity {

    private static String barSelection = null;
    private static int range = 1;
    private static String scatterX = null;
    private static String scatterY = null;
    private static String speciesGraph = null;
    private static int currentId = view_task.getCurrentId();
    private static String speciesTable = null;
    private static String baitTable = null;
    private static int startMonthTable;
    private static int startYearTable;
    private static int endMonthTable;
    private static int endYearTable;
    private static int startMonthGraph;
    private static int startYearGraph;
    private static int endMonthGraph;
    private static int endYearGraph;

    private static int dateRangeSelection = 2;
    private static int maxVisible = 15;

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
        LinearLayout dateLayout = findViewById(R.id.linearLayoutDate);
        EditText startDateEditTextGraph = findViewById(R.id.startDateGraph);
        EditText endDateEditTextGraph = findViewById(R.id.endDateGraph);
        TextView textViewSpeciesTable = findViewById(R.id.textViewSpeciesTable);
        textViewSpeciesTable.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        error.setText("");

        Spinner speciesSpinnerTable = findViewById(R.id.speciesSpinnerTable);
        List<String> fishNames = new ArrayList<>(view_task.fishNames);
        fishNames.add(0, "No Selection"); // Add "No Selection" as the first item
        ArrayAdapter<String> speciesAdapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, fishNames);
        speciesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        speciesSpinnerTable.setAdapter(speciesAdapter);

        Spinner baitSpinnerTable = findViewById(R.id.baitSpinnerTable);
        baitSpinnerTable.setAdapter(speciesAdapter);

        String[] barOptions = {"No Selection","Length", "Weight", "Bait", "Species", "Date"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item, barOptions);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        Spinner dropDownBar = findViewById(R.id.dropDownBar);
        dropDownBar.setAdapter(adapter);

        int currId = view_task.getCurrentId();
        ArrayList<String> speciesList = findSpecies(taskList.get(currId).getFish());
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item, speciesList);
        adapter3.setDropDownViewResource(R.layout.spinner_dropdown_item);
        Spinner speciesSpinnerGraph = findViewById(R.id.speciesSpinnerGraph);
        speciesSpinnerGraph.setAdapter(adapter3);

        startDateEditTextGraph.addTextChangedListener(new dateTextWatcher(startDateEditTextGraph));
        endDateEditTextGraph.addTextChangedListener(new dateTextWatcher(endDateEditTextGraph));
        startDateEditText.addTextChangedListener(new dateTextWatcher(startDateEditText));
        endDateEditText.addTextChangedListener(new dateTextWatcher(endDateEditText));

        dropDownBar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                barSelection = selectedItem;
                if(selectedItem == "Date") {
                    dateLayout.setVisibility(View.VISIBLE);
                    range.setVisibility(View.GONE);
                }else{
                    dateLayout.setVisibility(View.GONE);
                    range.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                barSelection = "No Selection";
            }
        });
        speciesSpinnerGraph.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                speciesGraph = selectedItem;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                speciesGraph = null;
            }
        });
        createGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
                SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
                String start = startDateEditTextGraph.getText().toString();
                String end = endDateEditTextGraph.getText().toString();
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
                            startMonthGraph = startMonthTemp;
                            startYearGraph = startYearTemp;
                            endMonthGraph = endMonthTemp;
                            endYearGraph = endYearTemp;
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
                    startMonthGraph = 1;
                    startYearGraph = 1800;
                    endMonthGraph =  Integer.parseInt(dateFormatMonth.format(calendar.getTime()));
                    endYearGraph =  Integer.parseInt(dateFormatYear.format(calendar.getTime()));
                }

                int rangeValue = 5;
                if(barSelection == "Length" || barSelection == "Weight"){
                    try{
                        rangeValue = Integer.parseInt(range.getText().toString());
                        setRange(rangeValue);
                    }catch (NumberFormatException e){
                        error.setText("Please enter a valid range.");
                        return;
                    }
                }
                if(scatterX == "No Selection" || scatterX == null){
                    if(barSelection == "Date"){
                        Intent switchToScatter = new Intent(getApplicationContext(), dateGraph.class);
                        startActivity(switchToScatter);
                    }else {
                        if(barSelection == "No Selection" || barSelection == null){
                            error.setText("Please select a valid option.");
                        }else {
                            Intent switchToGraphView = new Intent(getApplicationContext(), graphTest.class);
                            startActivity(switchToGraphView);
                        }
                    }
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
                            startMonthTable = startMonthTemp;
                            startYearTable = startYearTemp;
                            endMonthTable = endMonthTemp;
                            endYearTable = endYearTemp;
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
                    startMonthTable = 1;
                    startYearTable = 1800;
                    endMonthTable =  Integer.parseInt(dateFormatMonth.format(calendar.getTime()));
                    endYearTable =  Integer.parseInt(dateFormatYear.format(calendar.getTime()));
                }
                Intent switchToTable = new Intent(getApplicationContext(), viewTable.class);
                startActivity(switchToTable);

            }
        });
        speciesSpinnerTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                speciesTable = adapterView.getItemAtPosition(i).toString();
                if(i != 0) {
                    baitSpinnerTable.setSelection(0);
                    baitTable = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        baitSpinnerTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                baitTable = adapterView.getItemAtPosition(i).toString();
                if(i != 0) {
                    speciesSpinnerTable.setSelection(0);
                    speciesTable = null;
                }
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
    public void setRange(int range){this.range = range;}
    public static String getBarSelection(){return barSelection;}
    public static int getRange(){return range;}
    public static String getScatterX(){return scatterX;}
    public static String getScatterY(){return scatterY;}
    public static String getSpeciesGraph(){return speciesGraph;}
    public static String getSpeciesTable(){return speciesTable;}
    public static String getBaitTable(){return baitTable;}
    public static int getStartMonthTable(){return startMonthTable;}
    public static int getStartYearTable(){return startYearTable;}
    public static int getEndMonthTable(){return endMonthTable;}
    public static int getEndYearTable(){return endYearTable;}
    public static int getStartMonthGraph(){return startMonthGraph;}
    public static int getStartYearGraph(){return startYearGraph;}
    public static int getEndMonthGraph(){return endMonthGraph;}
    public static int getEndYearGraph(){return endYearGraph;}

    public static int getDateRangeSelection(){return dateRangeSelection;}
    public static void setDateRangeSelection(int range){dateRangeSelection = range;}

    public static int getMaxVisible(){return maxVisible;}
    public static void setMaxVisible(int range){maxVisible = range;}
}