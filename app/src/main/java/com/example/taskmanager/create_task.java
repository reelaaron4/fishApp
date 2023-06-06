package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class create_task extends AppCompatActivity {


   public static ArrayList<Task> taskList = new ArrayList<Task>();
   public static Task getTaskAtIndex(int index) {
       if (index >= 0 && index < taskList.size()) {
           return taskList.get(index);
       } else {
           return null;
       }
   }
   public void createTask(String name, String desc){
       Task taskObj = new Task(name, desc);
       addToList(taskObj);
   }
   public void addToList(Task task){
       taskList.add(task);
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);


        //add json code here

        // File path and name
        String filePath = getFilesDir().getAbsolutePath() + "/fishData/fishData.json";
        File file = new File(filePath);

        if (file.exists()) {
            Log.d("create_task", "File exists");
            // Read data from the existing file
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder jsonStringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonStringBuilder.append(line);
                }
                String jsonString = jsonStringBuilder.toString();

                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String description = jsonObject.getString("description");
                    Task task = new Task(name, description);
                    taskList.add(task);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Create a new file
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                Log.d("create_task", "File created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }





        Button editButton = findViewById(R.id.editButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button createButton = findViewById(R.id.createTaskButton);
        Button viewButton = findViewById(R.id.viewButton);
        Button createB = findViewById(R.id.createButton);
        Button saveButton = findViewById(R.id.saveButton);

        EditText inputName = (EditText)findViewById(R.id.createNameEditText);
        EditText inputDesc = (EditText)findViewById(R.id.createDescriptionEditText);

        TextView error = (TextView) findViewById(R.id.createErrorTextView);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameText = inputName.getText().toString();
                String descText = inputDesc.getText().toString();
                if(nameText.isEmpty()) {
                    error.setText("Invalid Input: a name must be entered");
                    return;
                }
                createTask(nameText, descText);
                //switch to view so user knows action succeeded
                Intent switchToView = new Intent(getApplicationContext(), view_task.class);
                startActivity(switchToView);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToEdit = new Intent(getApplicationContext(), edit_task.class);
                startActivity(switchToEdit);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToDelete = new Intent(getApplicationContext(), delete_task.class);
                startActivity(switchToDelete);

            }
        });

        viewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToView = new Intent(getApplicationContext(), databaseConnection.class);
                startActivity(switchToView);

            }
        });
        createB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToCreate = new Intent(getApplicationContext(), create_task.class);
                startActivity(switchToCreate);

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Convert the taskList ArrayList to a JSONArray
                JSONArray jsonArray = new JSONArray();
                for (Task task : taskList) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", task.getName());
                        jsonObject.put("description", task.getDescription());
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // File path and name
                String filePath = getFilesDir().getAbsolutePath() + "/fishData/fishData.json";
                File file = new File(filePath);

                // Save the JSON data to the file
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(jsonArray.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }



}