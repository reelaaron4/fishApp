package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class graph extends AppCompatActivity {

    public static String type = "length";
    public static int range = 1;
    public static String typeY = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        EditText range = findViewById(R.id.range);
        Button createGraph = findViewById(R.id.createGraph);

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
        createGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rangeValue = 5;
                try{
                    rangeValue = Integer.parseInt(range.getText().toString());
                    setRange(rangeValue);
                }catch (NumberFormatException e){
                    setRange(5);
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
    }
    public void setType(String type){this.type =  type;}
    public void setRange(int range){this.range = range;}
    public static String getType(){return type;}
    public static int getRange(){return range;}
    public static String getTypeY(){return typeY;}
}