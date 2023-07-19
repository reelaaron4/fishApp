package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
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
        Button createButton = findViewById(R.id.createButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button saveButton = findViewById(R.id.saveButton);
        Button addButton = findViewById(R.id.addNewSpecies);
        Spinner species = findViewById(R.id.addFishSpecies);
        EditText length = findViewById(R.id.addFishLength);
        EditText weight = findViewById(R.id.addFishWeight);
        EditText bait = findViewById(R.id.addFishBait);
        EditText temp = findViewById(R.id.addFishTemp);
        EditText misc = findViewById(R.id.addFishMisc);

        TextView location = findViewById(R.id.textViewTitle);

        TextView error = (TextView) findViewById(R.id.completeErrorTextView);

        int currentId = view_task.getCurrentId();
        String currentName= view_task.getCurrentName();
        location.setText(currentName);
        error.setText("");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, view_task.fishNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
                Date dateF = new Date();
                int idF = (taskList.get(currId).getFish().size());

                Fish fishTemp = new Fish(speciesF, lengthF, baitF, miscF, weightF, tempF, dateF, idF);
                fishTemp.setTaskID(currId);
                taskList.get(currId).setFishList(fishTemp);
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
        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToCreate = new Intent(getApplicationContext(), complete_task.class);
                startActivity(switchToCreate);

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToDelete = new Intent(getApplicationContext(), delete_task.class);
                startActivity(switchToDelete);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                JSONArray locationArray = new JSONArray();
                JSONArray speciesArray = new JSONArray();

                for (Task task : view_task.taskList) {
                    JSONObject location = new JSONObject();
                    try {
                        location.put("ID", task.getId());
                        location.put("name", task.getName());
                        location.put("description", task.getDescription());

                        JSONArray fishArray = new JSONArray();
                        for (Fish fish : task.getFish()){
                            JSONObject fishObject = new JSONObject();
                            fishObject.put("ID", fish.getId());
                            fishObject.put("species", fish.getSpecies());
                            fishObject.put("length", fish.getLength());
                            fishObject.put("weight", fish.getWeight());
                            fishObject.put("bait", fish.getBait());
                            fishObject.put("misc", fish.getMisc());
                            fishObject.put("temp", fish.getTemp());
                            fishObject.put("date", fish.getDate());
                            fishObject.put("id", fish.getId());

                            fishArray.put(fishObject);
                        }
                        location.put("fishList", fishArray);
                        locationArray.put(location);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for(String species : view_task.fishNames){
                    JSONObject speciesObject = new JSONObject();
                    try {
                        speciesObject.put("species", species);
                        speciesArray.put(speciesObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject resultObject = new JSONObject();
                try {
                    resultObject.put("locations", locationArray);
                    resultObject.put("species", speciesArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String fileName = "FishData.json";
                try {
                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                    fos.write(resultObject.toString().getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        saveButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent switchToImport = new Intent(getApplicationContext(), importExport.class);
                startActivity(switchToImport);
                return true;
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