package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class quickAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_add);

        Button quickAddButton = findViewById(R.id.quickAddButton);
        Button fullEntryButton = findViewById(R.id.fullEntryButton);
        Button viewButton = findViewById(R.id.viewFishButton);

        quickAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                int currId = view_task.getCurrentId();
                int idF = (taskList.get(currId).getFish().size());
                Fish fishTemp = new Fish("", 0.0, "", "", 0.0, 0.0, date, idF);
                taskList.get(currId).setFishList(fishTemp);

                Intent intent = new Intent(quickAdd.this, quickAdd.class);
                startActivity(intent);
            }
        });
        fullEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToCreateFish = new Intent(quickAdd.this, complete_task.class);
                startActivity(switchToCreateFish);
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToViewFish = new Intent(quickAdd.this, view_fish.class);
                startActivity(switchToViewFish);
            }
        });
    }
}