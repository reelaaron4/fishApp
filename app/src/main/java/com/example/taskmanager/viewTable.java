package com.example.taskmanager;

import static com.example.taskmanager.create_task.taskList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class viewTable extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);


        int currentId = view_task.getCurrentId();
        HashMap<String, ArrayList<Fish>> hash = new HashMap<String, ArrayList<Fish>>();
        ArrayList<Fish> fishList = (ArrayList<Fish>) taskList.get(currentId).getFish();
        for (Fish fish : fishList) {
            String species = fish.getSpecies();
            ArrayList<Fish> speciesList = hash.get(species);
            if(speciesList == null) {
                speciesList = new ArrayList<Fish>();
                hash.put(species, speciesList);
            }
            speciesList.add(fish);
        }



        //  ArrayList<Fish> temp = hash.get("Bass");
    }
}
