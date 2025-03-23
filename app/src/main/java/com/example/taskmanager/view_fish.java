package com.example.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class view_fish extends AppCompatActivity {
    public static int currentId = view_task.getCurrentId();
    public static String currentName = view_task.getCurrentName();
    public static int currentFishId = 0;
    private static String speciesSort = "No Selection";
    private static String lengthSort = "No Selection";
    private static String startDate = "";
    private static String endDate = "";
    private static String miscSort = "No Selection";
    private static boolean shouldSave = false;
    private static ArrayList<Fish> sortedList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fish);

        if(shouldSave){
            saveData();
            shouldSave = false;
        }
        Button saveButton = findViewById(R.id.saveButton);
        Button createButton = findViewById(R.id.createButton);
        Button graphButton = findViewById(R.id.graphButton);
        Button viewButton = findViewById(R.id.viewButton);
        TextView current = findViewById(R.id.textViewTitle);
        sortedList = sortListSpecies(speciesSort, view_task.getTaskAtIndex(currentId).fishList);
        sortedList = sortListLength(lengthSort, sortedList);
        sortedList = sortListDate(startDate, endDate, sortedList);
        sortedList = sortListMisc(miscSort, sortedList);

        fishAdapter adapter = new fishAdapter(this, sortedList);
        ListView listView = findViewById(R.id.fishListView);
        listView.setAdapter(adapter);
        current.setText(currentName);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // Get the number of items in the adapter
                int itemCount = adapter.getCount();

                // Calculate the index of the selected item based on the displayed order
                int selectedIndex = itemCount - i - 1;

                // Get the selected fish from the adapter using the calculated index
                Fish selectedFish = adapter.getItem(selectedIndex);

                currentFishId = selectedFish.getId();

                Intent switchToEditFish = new Intent(getApplicationContext(), edit_fish.class);
                startActivity(switchToEditFish);

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                saveData();
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
        graphButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToGraph = new Intent(getApplicationContext(), graph.class);
                startActivity(switchToGraph);
            }
        });
        graphButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent switchToSort = new Intent(getApplicationContext(), sortList.class);
                startActivity(switchToSort);
                return true;
            }
        });
        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToQuick = new Intent(getApplicationContext(), quickAdd.class);
                startActivity(switchToQuick);

            }
        });
        viewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToView = new Intent(getApplicationContext(), view_task.class);
                startActivity(switchToView);

            }
        });
    }
    public static int getCurrentFishId(){return currentFishId;}
    public static ArrayList<Fish> getSortedList(){return sortedList;}
    public static void setCurrentId(){currentId = view_task.getCurrentId();}
    public static void setCurrentName(){currentName = view_task.getCurrentName();}
    public static void setSpeciesSort(String species){speciesSort = species;}
    public static void setLengthSort(String length){lengthSort = length;}
    public static void setStartDate(String start){startDate = start;}
    public static void setEndDate(String end){endDate = end;}
    public static void setMiscSort(String misc){miscSort = misc;}
    public static void setShouldSave(boolean save){shouldSave = save;}

    private static ArrayList<Fish> sortListSpecies(String species, ArrayList<Fish> list){
        ArrayList<Fish> newList = new ArrayList<>();
        if(!species.equals("No Selection")) {
            for (Fish fish : list) {
                if (fish.getSpecies().equals(species)) {
                    newList.add(fish);
                }
            }
            return newList;
        }else{
           return view_task.getTaskAtIndex(currentId).fishList;
        }
    }
    private static ArrayList<Fish> sortListLength(String length, ArrayList<Fish> list){
        ArrayList<Fish> newList = new ArrayList<>();
        newList.addAll(list);
        for (int i = 0; i < newList.size() - 1; i++) {
           for(int j = 0; j < newList.size() - i - 1; j++){
               if(length == "Descending"){
                   if(newList.get(j).getLength() > newList.get(j + 1).getLength()){
                       Fish temp = newList.get(j);
                       newList.set(j, newList.get(j + 1));
                       newList.set(j + 1, temp);
                   }
               }else if(length == "Ascending"){
                   if(newList.get(j).getLength() < newList.get(j + 1).getLength()){
                       Fish temp = newList.get(j);
                       newList.set(j, newList.get(j + 1));
                       newList.set(j + 1, temp);
                   }
               }
           }
        }
        return newList;
    }
    private static ArrayList<Fish> sortListDate(String start, String end, ArrayList<Fish> list){
        if(start == "" || end == ""){
            return list;
        }
        int startMonthTemp = Integer.parseInt(start.substring(0, 2));
        int startYearTemp = Integer.parseInt(start.substring(3));
        int endMonthTemp = Integer.parseInt(end.substring(0, 2));
        int endYearTemp = Integer.parseInt(end.substring(3));
        ArrayList<Fish> newList = new ArrayList<>();
        for(Fish fish : list){
            if(isWithinDateRange(fish.getDate(), startMonthTemp, startYearTemp, endMonthTemp, endYearTemp)) {
               newList.add(fish);
            }
        }

        return newList;
    }
    private static ArrayList<Fish> sortListMisc(String misc, ArrayList<Fish> list){
        ArrayList<Fish> newList = new ArrayList<>();
        if(!misc.equals("No Selection")) {
            for (Fish fish : list) {
                if(fish.getMisc().equals(misc)) {
                    newList.add(fish);
                }else if(fish.getMisc2().equals(misc)){
                    newList.add(fish);
                }else if(fish.getMisc3().equals(misc)){
                    newList.add(fish);
                }
            }
            return newList;
        }else{
            return list;
        }
    }
    private static boolean isWithinDateRange(Date date, int startMonth, int startYear, int endMonth, int endYear){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return (year > startYear && year < endYear) ||
                (year == startYear && month >= startMonth) ||
                (year == endYear && month <= endMonth);
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
