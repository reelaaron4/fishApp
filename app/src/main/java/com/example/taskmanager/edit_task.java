package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class edit_task extends AppCompatActivity {
    public static void editTask(ArrayList<Task> taskList, int id, String name, String description) {
        // Get the task with the given id
        Task task = taskList.get(id);

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
        Button deleteButton = findViewById(R.id.deleteTaskButton);
        EditText inputName = findViewById(R.id.editNameEditText);
        EditText inputDesc = findViewById(R.id.editDescriptionEditText);
        TextView error = findViewById(R.id.editErrorTextView);

        inputName.setText(taskList.get(view_task.getCurrentId()).getName());
        inputDesc.setText(taskList.get(view_task.getCurrentId()).getDescription());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int taskId = view_task.getCurrentId();
                String name = inputName.getText().toString();
                String description = inputDesc.getText().toString();
                //validates user input to be in range of list
                if(taskId < 0 || taskId > taskList.size()){
                    error.setText("Invalid Input: Number must be a valid ID");
                    return;
                }

                editTask(taskList, taskId, name, description);
                //switch to view so user knows action succeeded
                Intent switchToView = new Intent(getApplicationContext(), view_task.class);
                startActivity(switchToView);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButton.setEnabled(false);
                int currId = view_task.getCurrentId();
                tasksAdapter adapter = new tasksAdapter(view.getContext(), taskList);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(edit_task.this);
                builder1.setMessage("Are you sure you want to delete?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Remove the fish with the given id from the list
                                taskList.remove(currId);

                                // Update the id of all the fish in the list
                                for (int i = currId; i < taskList.size(); i++) {
                                    Task tempTask  = taskList.get(i);
                                    tempTask.setId(i);
                                }

                                // Notify the adapter that the data set has changed
                                adapter.notifyDataSetChanged();
                                dialog.cancel();
                                Intent switchToView = new Intent(getApplicationContext(), view_task.class);
                                startActivity(switchToView);
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteButton.setEnabled(true);
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

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