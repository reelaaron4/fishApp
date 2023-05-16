package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        fishAdapter adapter = new fishAdapter(this, create_task.getTaskAtIndex(currentId).fishList);
        ListView listView = findViewById(R.id.fishListView);
        listView.setAdapter(adapter);
        current.setText(currentName);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // Get the number of items in the adapter
                int itemCount = adapter.getCount();

                // Calculate the index of the selected item based on the displayed order
                int selectedIndex = itemCount - i - 1;

                // Get the selected fish from the adapter using the calculated index
                Fish selectedFish = adapter.getItem(selectedIndex);

                currentFishId = selectedIndex;

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
