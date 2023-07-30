package com.example.taskmanager;

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

public class addSpecies extends AppCompatActivity {
    String speciesSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_species);

        Button addButton = findViewById(R.id.addNewSpecies);
        Button deleteButton = findViewById(R.id.deleteSpecies);
        EditText speciesEditText = findViewById(R.id.newSpeciesEditText);
        Spinner speciesSpinner = findViewById(R.id.speciesSpinner);
        TextView error = findViewById(R.id.addSpeciesErrorTextView);
        Button viewButton = findViewById(R.id.viewButton);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, view_task.fishNames);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        speciesSpinner.setAdapter(adapter);

        speciesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                speciesSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String species = speciesEditText.getText().toString();
                if(!species.isEmpty()){
                    view_task.fishNames.add(species);
                }else{
                    error.setText("Please enter a species name!");
                    return;
                }
                Intent switchToFish = new Intent(getApplicationContext(), complete_task.class);
                startActivity(switchToFish);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(speciesSelected != null){
                    try {
                        view_task.fishNames.remove(speciesSelected);
                    }catch (Exception e){
                        error.setText("Please select a species to delete!");
                        return;
                    }
                }else{
                    error.setText("Please select a species to delete!");
                    return;
                }
                Intent switchToFish = new Intent(getApplicationContext(), complete_task.class);
                startActivity(switchToFish);
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToFish = new Intent(getApplicationContext(), view_fish.class);
                startActivity(switchToFish);
            }
        });
    }

}