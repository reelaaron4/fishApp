package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

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
                    error.setText("Invalid Input: Number must be a valid ID");
                    return;
                } else {
                    try {
                        taskId = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        error.setText("Invalid Input: Number must be a valid ID");
                        return;
                    }
                }

                String name = inputName.getText().toString();
                String description = inputDesc.getText().toString();
                //validates user input to be in range of list
                if(taskId < 1 || taskId > taskList.size()){
                    error.setText("Invalid Input: Number must be a valid ID");
                    return;
                }

                editTask(taskList, taskId, name, description);
                //switch to view so user knows action succeeded
                Intent switchToView = new Intent(getApplicationContext(), view_task.class);
                startActivity(switchToView);
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
}