package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class edit_fish extends AppCompatActivity {

   public static int errorCount = 0;
   private static int currId;
   private static int currentFish;
   private static String speciesSelected = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fish);
        currId = view_task.getCurrentId();
        currentFish = view_fish.getCurrentFishId();
        Spinner species = findViewById(R.id.editFishSpecies);
        EditText length = findViewById(R.id.editFishLength);
        EditText weight = findViewById(R.id.editFishWeight);
        EditText bait = findViewById(R.id.editFishBait);
        EditText temp = findViewById(R.id.editFishTemp);
        EditText misc = findViewById(R.id.editFishMisc);
        EditText misc2 = findViewById(R.id.editFishMisc2);
        EditText date = findViewById(R.id.editFishDate);
        Button editButton = findViewById(R.id.editFishButton);
        Button viewButton = findViewById(R.id.viewButton);
        Button deleteButton = findViewById(R.id.deleteFishButton);
        TextView error = findViewById(R.id.errorTextView);
        error.setText("");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item, view_task.fishNames);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        species.setAdapter(adapter);
        for(String speciesTemp : view_task.fishNames){
            if(speciesTemp.equals(taskList.get(currId).getFish().get(currentFish).getSpecies())){
                species.setSelection(view_task.fishNames.indexOf(speciesTemp));
                break;
            }
        }
        species.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                speciesSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        length.setText(String.valueOf(taskList.get(currId).getFish().get(currentFish).getLength()));
        weight.setText(String.valueOf(taskList.get(currId).getFish().get(currentFish).getWeight()));
        bait.setText(taskList.get(currId).getFish().get(currentFish).getBait());
        temp.setText(String.valueOf(taskList.get(currId).getFish().get(currentFish).getTemp()));
        misc.setText(taskList.get(currId).getFish().get(currentFish).getMisc());
        misc2.setText(taskList.get(currId).getFish().get(currentFish).getMisc2());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM d, yyyy h:mm a\n", Locale.getDefault());
        date.setText(dateFormat.format(taskList.get(currId).getFish().get(currentFish).getDate()));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String speciesF = speciesSelected;
                double lengthF = Double.parseDouble(length.getText().toString());
                double weightF = (weight.getText().toString().isEmpty()) ? 0.0: Double.parseDouble(weight.getText().toString());
                String baitF = bait.getText().toString();
                double tempF = (temp.getText().toString().isEmpty()) ? 0.0: Double.parseDouble(temp.getText().toString());
                String miscF = (misc.getText().toString().isEmpty()) ? "": misc.getText().toString();
                String miscF2 = (misc2.getText().toString().isEmpty()) ? "": misc2.getText().toString();
                Date dateF = null;
                try{
                    dateF = (date.getText().toString().isEmpty()) ? taskList.get(currId).getFish().get(currentFish).getDate() : dateFormat.parse(date.getText().toString());
                }catch(Exception e){
                    error.setText("Invalid date format!");
                    dateF = taskList.get(currId).getFish().get(currentFish).getDate();
                }

                (taskList.get(currId).getFish().get(currentFish)).setSpecies(speciesF);
                (taskList.get(currId).getFish().get(currentFish)).setLength(lengthF);
                (taskList.get(currId).getFish().get(currentFish)).setBait(baitF);
                (taskList.get(currId).getFish().get(currentFish)).setWeight(weightF);
                (taskList.get(currId).getFish().get(currentFish)).setTemp(tempF);
                (taskList.get(currId).getFish().get(currentFish)).setMisc(miscF);
                (taskList.get(currId).getFish().get(currentFish)).setMisc2(miscF2);
                (taskList.get(currId).getFish().get(currentFish)).setDate(dateF);

                //flag auto save
                view_fish.setShouldSave(true);

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

                AlertDialog.Builder builder1 = new AlertDialog.Builder(edit_fish.this);
                builder1.setMessage("Are you sure you want to delete?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Remove the fish with the given id from the list
                                taskList.get(currId).getFish().remove(currentFish);

                                // Update the id of all the fish in the list
                                for (int i = currentFish; i < taskList.get(currId).getFish().size(); i++) {
                                    Fish currentFish2 = (Fish) taskList.get(currId).getFish().get(i);
                                    currentFish2.setId(i);
                                }

                                // Notify the adapter that the data set has changed
                                adapter.notifyDataSetChanged();
                                dialog.cancel();
                                Intent switchToView = new Intent(getApplicationContext(), view_fish.class);
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
    }
    //public static void setCurrentFishId(){currentFishId = view_fish.getCurrentFishId();}
}