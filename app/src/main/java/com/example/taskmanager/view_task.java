package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class view_task extends AppCompatActivity {
    private static String currentName;
    private static int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        Button editButton = findViewById(R.id.editButton);
        Button createButton = findViewById(R.id.createButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button viewButton = findViewById(R.id.viewButton);
        tasksAdapter adapter = new tasksAdapter(this, create_task.taskList);

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

                Intent switchToComplete = new Intent(getApplicationContext(), complete_task.class);
                startActivity(switchToComplete);

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