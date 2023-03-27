package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class view_fish extends AppCompatActivity {
    public static int currentId = view_task.getCurrentId();
    public static String currentName = view_task.getCurrentName();
    public static int currentFishId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fish);

        Button editButton = findViewById(R.id.editButton);
        Button createButton = findViewById(R.id.createButton);
        Button graphButton = findViewById(R.id.graphButton);
        Button viewButton = findViewById(R.id.viewButton);
        TextView current = findViewById(R.id.textView2);

        /*fishAdapter adapter = new fishAdapter(this, create_task.taskList.getTaskAtIndex(currentId).getFish);
        ListView listview = findViewById(R.id.fishListView);*/
        fishAdapter adapter = new fishAdapter(this, create_task.getTaskAtIndex(currentId).fishList);
        ListView listView = findViewById(R.id.fishListView);
        listView.setAdapter(adapter);
        current.setText(currentName);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Object selectedObject = adapterView.getItemAtPosition(i);

                currentFishId = i;

                Intent switchToEditFish = new Intent(getApplicationContext(), edit_fish.class);
                startActivity(switchToEditFish);

            }
        });
        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToEdit = new Intent(getApplicationContext(), edit_fish.class);
                startActivity(switchToEdit);

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
        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchToCreate = new Intent(getApplicationContext(), complete_task.class);
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
    public static int getCurrentFishId(){return currentFishId;}
    public static void setCurrentId(){currentId = view_task.getCurrentId();}
    public static void setCurrentName(){currentName = view_task.getCurrentName();}
}
