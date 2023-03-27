package com.example.taskmanager;

import static com.example.taskmanager.create_task.taskList;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.taskmanager.databinding.ActivityEditFishBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class edit_fish extends AppCompatActivity {

   // public static int currentFishId = view_fish.getCurrentFishId();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fish);

        EditText species = findViewById(R.id.editFishSpecies);
        EditText length = findViewById(R.id.editFishLength);
        EditText weight = findViewById(R.id.editFishWeight);
        EditText bait = findViewById(R.id.editFishBait);
        EditText temp = findViewById(R.id.editFishTemp);
        EditText weather = findViewById(R.id.editFishWeather);
        EditText date = findViewById(R.id.editFishDate);
        Button editButton = findViewById(R.id.editFishButton);
        Button viewButton = findViewById(R.id.viewButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button enterFishButton = findViewById(R.id.createButton);
        Button bottomEditButton = findViewById(R.id.editButton);
        TextView error = findViewById(R.id.errorTextView);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String speciesF = species.getText().toString();
                double lengthF = Double.parseDouble(length.getText().toString());
                double weightF = Double.parseDouble(weight.getText().toString());
                String baitF = bait.getText().toString();
                double tempF = Double.parseDouble(temp.getText().toString());
                String weatherF = weather.getText().toString();
                Date dateF = null;
                try {
                    SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.getDefault());
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String dateInputString = date.getText().toString();
                    Date dateInput = inputDateFormat.parse(dateInputString);
                    String dateOutputString = outputDateFormat.format(dateInput);
                    dateF = outputDateFormat.parse(dateOutputString);
                } catch (ParseException e) {
                    //FIX LATER TO DISPLAY RED TEXT!!!!!!!!!!!!!!!!!!!!!!!!!!
                    e.printStackTrace();
                }



                //  FIND WAY TO MAKE ENTERING DATE EASY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                int currId = view_task.getCurrentId();
                int currentFish = view_fish.getCurrentFishId();
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setSpecies(speciesF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setLength(lengthF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setBait(baitF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setWeight(weightF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setTemp(tempF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setWeather(weatherF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setDate(dateF);



                //switch to view so user knows action succeeded
                Intent switchToFish = new Intent(getApplicationContext(), view_fish.class);
                startActivity(switchToFish);
            }

        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToView = new Intent(getApplicationContext(), view_fish.class);
                startActivity(switchToView);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButton.setEnabled(false);
                int currId = view_task.getCurrentId();
                int currentFish = view_fish.getCurrentFishId();
                fishAdapter adapter = new fishAdapter(view.getContext(), (ArrayList<Fish>) taskList.get(currId).getFish());
                // Remove the fish with the given id from the list
                taskList.get(currId).getFish().remove(currentFish);

                // Update the id of all the fish in the list
                for (int i = currentFish; i < taskList.get(currId).getFish().size(); i++) {
                    Fish currentFish2 = (Fish) taskList.get(currId).getFish().get(i);
                    currentFish2.setId(i);
                }

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();


                Intent switchToView = new Intent(getApplicationContext(), view_fish.class);
                startActivity(switchToView);

            }
        });
        enterFishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToEnter = new Intent(getApplicationContext(), complete_task.class);
                startActivity(switchToEnter);
            }
        });
        bottomEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToEdit = new Intent(getApplicationContext(), edit_fish.class);
                startActivity(switchToEdit);
            }
        });
    }
    //public static void setCurrentFishId(){currentFishId = view_fish.getCurrentFishId();}
}