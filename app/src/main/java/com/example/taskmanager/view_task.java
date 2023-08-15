package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class view_task extends AppCompatActivity {
    private static String currentName;
    private static int currentId;
    public static ArrayList<Task> taskList = new ArrayList<Task>();
    public static ArrayList<String> fishNames = new ArrayList<String>();

    public static void createTask(String name, String desc){
        Task taskObj = new Task(name, desc);
        addToList(taskObj);
    }
    public static Task getTaskAtIndex(int index) {
        if (index >= 0 && index < taskList.size()) {
            return taskList.get(index);
        } else {
            return null;
        }
    }
    public static void addToList(Task task){
        taskList.add(task);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        // Add JSON code here
        if(taskList.isEmpty()) {
            String fileName = "FishData.json";
            try {
                FileInputStream fis = openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                fis.close();

                // Parse the JSON data and populate taskList
                JSONObject fishDataObject = new JSONObject(stringBuilder.toString());
                JSONArray locationListArray = fishDataObject.getJSONArray("locations");
                for (int i = 0; i < locationListArray.length(); i++) {
                    JSONObject jsonObject = locationListArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String description = jsonObject.getString("description");
                    Task task = new Task(name, description);


                    JSONArray fishArray = jsonObject.getJSONArray("fishList");

                    for (int j = 0; j < fishArray.length(); j++){
                        JSONObject fishObject = fishArray.getJSONObject(j);
                        String ID = fishObject.getString("ID");
                        String species = fishObject.getString("species");
                        String lengthS = fishObject.getString("length");
                        String weightS = fishObject.getString("weight");
                        String bait = fishObject.getString("bait");
                        String tempS = fishObject.getString("temp");
                        String misc = fishObject.getString("misc");
                        String misc2 = "";
                        try{
                            misc2 = fishObject.getString("misc2");
                        }catch (JSONException e){
                            misc2 = "";
                        }
                        String dateS = fishObject.getString("date");
                        String idS = fishObject.getString("id");

                        double length = Double.parseDouble(lengthS);
                        double weight = Double.parseDouble(weightS);
                        double temp = Double.parseDouble(tempS);
                        int id = Integer.parseInt(idS);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        Date date = null;
                        try {
                            date = dateFormat.parse(dateS);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //Date date = new Date();


                        Fish fish = new Fish(species, length, bait, misc, misc2, weight, temp, date, id);
                        task.setFishList(fish);
                    }

                    taskList.add(task);
                }
                JSONArray speciesArray = fishDataObject.getJSONArray("species");
                for (int i = 0; i < speciesArray.length(); i++) {
                    JSONObject jsonObject = speciesArray.getJSONObject(i);
                    String name = jsonObject.getString("species");
                    fishNames.add(name);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        Button createButton = findViewById(R.id.createButton);
        Button viewButton = findViewById(R.id.viewButton);
        tasksAdapter adapter = new tasksAdapter(this, taskList);

        ListView listView = findViewById(R.id.taskListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // Get the number of items in the adapter
                int itemCount = adapter.getCount();

                // Calculate the index of the selected item based on the displayed order
                int selectedIndex = itemCount - i - 1;

                // Get the selected task from the adapter using the calculated index
                Task selectedTask = adapter.getItem(selectedIndex);

                // Extract the name and id attributes from the selected task
                currentName = selectedTask.getName();
                currentId = selectedTask.getId();
                view_fish.setCurrentId();
                view_fish.setCurrentName();

                Intent switchToQuickAdd = new Intent(getApplicationContext(), quickAdd.class);
                startActivity(switchToQuickAdd);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // Get the number of items in the adapter
                int itemCount = adapter.getCount();

                // Calculate the index of the selected item based on the displayed order
                int selectedIndex = itemCount - i - 1;

                // Get the selected task from the adapter using the calculated index
                Task selectedTask = adapter.getItem(selectedIndex);

                // Extract the name and id attributes from the selected task
                currentName = selectedTask.getName();
                currentId = selectedTask.getId();

                Intent switchToEditLocation = new Intent(getApplicationContext(), edit_task.class);
                startActivity(switchToEditLocation);
                return true;
            }
        });

        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToCreate = new Intent(getApplicationContext(), create_task.class);
                startActivity(switchToCreate);

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
    public static String getCurrentName() {
        return currentName;
    }
    public static int getCurrentId(){return currentId;}
}