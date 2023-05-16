package com.example.taskmanager;

import static com.example.taskmanager.create_task.taskList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class edit_task extends AppCompatActivity {
    public static void editTask(ArrayList<Task> taskList, int id, String name, String description) {
        // Get the task with the given id
        Task task = taskList.get(id - 1);

        // Update the name and description of the task
        task.setName(name);
        task.setDescription(description);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Button editButton = findViewById(R.id.editTaskButton);
        Button createButton = findViewById(R.id.createButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button viewButton = findViewById(R.id.viewButton);

        EditText inputID = findViewById(R.id.editTaskEditText);
        EditText inputName = findViewById(R.id.editNameEditText);
        EditText inputDesc = findViewById(R.id.editDescriptionEditText);

        TextView error = findViewById(R.id.editErrorTextView);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputID.getText().toString();
                int taskId = 0;
                if (input.isEmpty()) {
                    error.setText("Invalid Input: Number must be between 1 and the number of tasks");
                    return;
                } else {
                    try {
                        taskId = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        error.setText("Invalid Input: Number must be between 1 and the number of tasks");
                        return;
                    }
                }

                String name = inputName.getText().toString();
                String description = inputDesc.getText().toString();
                //validates user input to be in range of list
                if(taskId < 1 || taskId > taskList.size()){
                    error.setText("Invalid Input: Number must be between 1 and the number of tasks");
                    return;
                }

                editTask(taskList, taskId, name, description);
                //switch to view so user knows action succeeded
                Intent switchToView = new Intent(getApplicationContext(), view_task.class);
                startActivity(switchToView);
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

        createButton.setOnClickListener(new View.OnClickListener()
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