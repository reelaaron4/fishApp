package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        Button editButton = findViewById(R.id.editButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button createButton = findViewById(R.id.createTaskButton);
        Button viewButton = findViewById(R.id.viewButton);
        Button createB = findViewById(R.id.createButton);

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
                Intent switchToView = new Intent(getApplicationContext(), view_task.class);
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
    }



}