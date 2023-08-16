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

import java.util.ArrayList;
import java.util.Date;

public class complete_task extends AppCompatActivity {
    public static ArrayList<Fish> fishList = new ArrayList<Fish>();
    public static int errorCount = 0;
    public String speciesSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_task);
        Button fishButton = findViewById(R.id.enterFishButton);
        Button viewFishButton = findViewById(R.id.viewFishButton);
        Button viewButton = findViewById(R.id.viewButton);
        Button addButton = findViewById(R.id.addNewSpecies);
        Spinner species = findViewById(R.id.addFishSpecies);
        EditText length = findViewById(R.id.addFishLength);
        EditText weight = findViewById(R.id.addFishWeight);
        EditText bait = findViewById(R.id.addFishBait);
        EditText temp = findViewById(R.id.addFishTemp);
        EditText misc = findViewById(R.id.addFishMisc);
        EditText misc2 = findViewById(R.id.addFishMisc2);

        TextView location = findViewById(R.id.textViewTitle);

        TextView error = (TextView) findViewById(R.id.completeErrorTextView);

        int currentId = view_task.getCurrentId();
        String currentName= view_task.getCurrentName();
        location.setText(currentName);
        error.setText("");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item, view_task.fishNames);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        species.setAdapter(adapter);
        species.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              speciesSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        fishButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(speciesSelected.isEmpty() || length.getText().toString().isEmpty() || bait.getText().toString().isEmpty()){
                    if(errorCount == 0) {
                        error.setText("Complete all required fields!");
                        errorCount++;
                    }else{
                        error.setText("Complete all required fields!!!");
                        errorCount--;
                    }
                    return;
                }
                int currId = view_task.getCurrentId();
                String speciesF = speciesSelected;
                double lengthF = Double.parseDouble(length.getText().toString());
                double weightF = (weight.getText().toString().isEmpty()) ? 0.0: Double.parseDouble(weight.getText().toString());
                String baitF = bait.getText().toString();
                double tempF = (temp.getText().toString().isEmpty()) ? 0.0: Double.parseDouble(temp.getText().toString());
                String miscF = (misc.getText().toString().isEmpty()) ? "": misc.getText().toString();
                String miscF2 = (misc2.getText().toString().isEmpty()) ? "": misc2.getText().toString();
                Date dateF = new Date();
                int idF = (taskList.get(currId).getFish().size());

                Fish fishTemp = new Fish(speciesF, lengthF, baitF, miscF, miscF2, weightF, tempF, dateF, idF);
                fishTemp.setTaskID(currId);
                taskList.get(currId).setFishList(fishTemp);

                //flag autoSave
                view_fish.setShouldSave(true);

                //switch to view so user knows action succeeded
                Intent switchToFish = new Intent(getApplicationContext(), view_fish.class);
                startActivity(switchToFish);
            }
        });
        viewFishButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {//switch to view so user knows action succeeded
                Intent switchToFish = new Intent(getApplicationContext(), view_fish.class);
                startActivity(switchToFish);
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
        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToAdd = new Intent(getApplicationContext(), addSpecies.class);
                startActivity(switchToAdd);

            }
        });

    }
}