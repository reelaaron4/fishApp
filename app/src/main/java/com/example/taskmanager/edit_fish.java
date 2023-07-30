package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class edit_fish extends AppCompatActivity {

   public static int errorCount = 0;
   private static int currId;
   private static int currentFish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fish);
        currId = view_task.getCurrentId();
        currentFish = view_fish.getCurrentFishId();
        EditText species = findViewById(R.id.editFishSpecies);
        EditText length = findViewById(R.id.editFishLength);
        EditText weight = findViewById(R.id.editFishWeight);
        EditText bait = findViewById(R.id.editFishBait);
        EditText temp = findViewById(R.id.editFishTemp);
        EditText misc = findViewById(R.id.editFishMisc);
        EditText date = findViewById(R.id.editFishDate);
        Button editButton = findViewById(R.id.editFishButton);
        Button viewButton = findViewById(R.id.viewButton);
        Button deleteButton = findViewById(R.id.deleteFishButton);
        TextView error = findViewById(R.id.errorTextView);
        error.setText("");
        species.setText(taskList.get(currId).getFish().get(currentFish).getSpecies());
        length.setText(String.valueOf(taskList.get(currId).getFish().get(currentFish).getLength()));
        weight.setText(String.valueOf(taskList.get(currId).getFish().get(currentFish).getWeight()));
        bait.setText(taskList.get(currId).getFish().get(currentFish).getBait());
        temp.setText(String.valueOf(taskList.get(currId).getFish().get(currentFish).getTemp()));
        misc.setText(taskList.get(currId).getFish().get(currentFish).getMisc());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a\n", Locale.getDefault());
        date.setText(dateFormat.format(taskList.get(currId).getFish().get(currentFish).getDate()));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(species.getText().toString().isEmpty() || length.getText().toString().isEmpty() || bait.getText().toString().isEmpty()){
                    if(errorCount == 0) {
                        error.setText("Complete all required fields!");
                        errorCount++;
                    }else{
                        error.setText("Complete all required fields!!!");
                        errorCount--;
                    }
                    return;
                }
                String speciesF = species.getText().toString();
                double lengthF = Double.parseDouble(length.getText().toString());
                double weightF = (weight.getText().toString().isEmpty()) ? 0.0: Double.parseDouble(weight.getText().toString());
                String baitF = bait.getText().toString();
                double tempF = (temp.getText().toString().isEmpty()) ? 0.0: Double.parseDouble(temp.getText().toString());
                String miscF = (misc.getText().toString().isEmpty()) ? "": misc.getText().toString();
                Date dateF = null;
                try{
                    dateF = (date.getText().toString().isEmpty()) ? taskList.get(currId).getFish().get(currentFish).getDate() : dateFormat.parse(date.getText().toString());
                }catch(Exception e){
                    error.setText("Invalid date format!");
                    dateF = taskList.get(currId).getFish().get(currentFish).getDate();
                }

                ((Fish) taskList.get(currId).getFish().get(currentFish)).setSpecies(speciesF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setLength(lengthF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setBait(baitF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setWeight(weightF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setTemp(tempF);
                ((Fish) taskList.get(currId).getFish().get(currentFish)).setMisc(miscF);
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
    }
    //public static void setCurrentFishId(){currentFishId = view_fish.getCurrentFishId();}
}