package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class quickAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_add);

        Button quickAddButton = findViewById(R.id.quickAddButton);
        Button fullEntryButton = findViewById(R.id.fullEntryButton);
        Button viewButton = findViewById(R.id.viewFishButton);


        quickAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                int currId = view_task.getCurrentId();
                int idF = (taskList.get(currId).getFish().size());
                Fish fishTemp = new Fish("", 0.0, "", "", "", "",0.0, 0.0, date, idF);
                taskList.get(currId).setFishList(fishTemp);

                saveData();

                Intent intent = new Intent(quickAdd.this, quickAdd.class);
                startActivity(intent);
            }
        });
        fullEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToCreateFish = new Intent(quickAdd.this, complete_task.class);
                startActivity(switchToCreateFish);
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToViewFish = new Intent(quickAdd.this, view_fish.class);
                startActivity(switchToViewFish);
            }
        });
    }
    public void saveData(){
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
                    fishObject.put("misc2", fish.getMisc2());
                    fishObject.put("misc3", fish.getMisc3());
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
        String fileTemp = "FishDataTemp.json";
        try {
            // Step 1: Write data to the temporary file
            FileOutputStream tempFos = openFileOutput(fileTemp, Context.MODE_PRIVATE);
            tempFos.write(resultObject.toString().getBytes());
            tempFos.close();

            // Step 2: Perform atomic file replacement
            File tempFile = new File(getFilesDir(), fileTemp);
            File originalFile = new File(getFilesDir(), fileName);

            if (tempFile.renameTo(originalFile)) {
                // Success: The data was saved and renamed atomically
            } else {
                System.out.println("FAILED TO SAVE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}