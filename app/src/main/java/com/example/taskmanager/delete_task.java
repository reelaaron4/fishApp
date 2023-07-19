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
                    error.setText("Invalid Input: Number must be a valid ID");
                    return;
                } else {
                    try {
                        taskId = Integer.parseInt(input) - 1;
                    } catch (NumberFormatException e) {
                        error.setText("Invalid Input: Number must be a valid ID");
                        return;
                    }
                }
                //validates user input to be in range of list
                if(taskId < 0 || taskId >= taskList.size()){
                    error.setText("Invalid Input: Number must be a valid ID");
                    return;
                }

                removeTask(taskList, taskId, adapter);
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