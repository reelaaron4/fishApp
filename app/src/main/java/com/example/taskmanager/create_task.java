package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class create_task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Button createButton = findViewById(R.id.createTaskButton);
        Button viewButton = findViewById(R.id.viewButton);
        EditText inputName = (EditText) findViewById(R.id.createNameEditText);
        EditText inputDesc = (EditText) findViewById(R.id.createDescriptionEditText);

        TextView error = (TextView) findViewById(R.id.createErrorTextView);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameText = inputName.getText().toString();
                String descText = inputDesc.getText().toString();
                if (nameText.isEmpty()) {
                    error.setText("Invalid Input: a name must be entered");
                    return;
                }
                view_task.createTask(nameText, descText);

                //flag auto save
                view_task.setShouldSave(true);

                // Switch to view so the user knows the action succeeded
                Intent switchToView = new Intent(getApplicationContext(), view_task.class);
                startActivity(switchToView);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToView = new Intent(getApplicationContext(), view_task.class);
                startActivity(switchToView);
            }
        });

    }
}
