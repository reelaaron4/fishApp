package com.example.taskmanager;

import static com.example.taskmanager.create_task.taskList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class delete_task extends AppCompatActivity {

    public static void removeTask(ArrayList<Task> taskList, int id, tasksAdapter adapter) {
        // Remove the task with the given id from the list
        taskList.remove(id);

        // Update the id of all the tasks in the list
        for (int i = id; i < taskList.size(); i++) {
            Task currentTask = taskList.get(i);
            currentTask.setId(i);
        }

        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);

        Button deleteButton = findViewById(R.id.deleteTaskButton);
        Button editButton = findViewById(R.id.editButton);
        Button createButton = findViewById(R.id.createButton);
        Button viewButton = findViewById(R.id.viewButton);

        EditText inputID = (EditText)findViewById(R.id.deleteTaskEditText);

        TextView error = (TextView) findViewById(R.id.deleteErrorTextView);

        tasksAdapter adapter = new tasksAdapter(this, taskList);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButton.setEnabled(false);
                String input = inputID.getText().toString();
                int taskId = -1;
                if (input.isEmpty()) {
                    error.setText("Invalid Input: Number must be between 1 and the number of tasks");
                    return;
                } else {
                    try {
                        taskId = Integer.parseInt(input) - 1;
                    } catch (NumberFormatException e) {
                        error.setText("Invalid Input: Number must be between 1 and the number of tasks");
                        return;
                    }
                }
                //validates user input to be in range of list
                if(taskId < 0 || taskId >= taskList.size()){
                    error.setText("Invalid Input: Number must be between 1 and the number of tasks");
                    return;
                }

                removeTask(taskList, taskId, adapter);
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