package com.example.taskmanager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class importExport extends AppCompatActivity {

    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export);

        Button viewButton = findViewById(R.id.viewFish);
        Button importButton = findViewById(R.id.importButton);
        Button exportButton = findViewById(R.id.exportButton);
        error = findViewById(R.id.importError);

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");  // Set the desired file type(s) here

                // Optionally, you can restrict the file picker to the Downloads folder
                Uri downloadsUri = Uri.parse("content://com.android.providers.downloads.documents/document/primary:Download");
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, downloadsUri);

                startActivityForResult(intent, 1);
            }
        });


        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sourceFileName = "FishData.json";
                File sourceFile = new File(getFilesDir(), sourceFileName);

                File documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File destinationFile = new File(documentsDirectory, sourceFileName);

                try {
                    InputStream inputStream = new FileInputStream(sourceFile);
                    OutputStream outputStream = new FileOutputStream(destinationFile);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }

                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();

                    error.setTextColor(Color.GREEN);
                    error.setText("Export Successful! The File is in the Downloads Folder");

                } catch (IOException e) {
                    e.printStackTrace();
                    error.setTextColor(Color.RED);
                    error.setText("Export Failed! Check App Permissions in the Settings");
                }
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(importExport.this, view_fish.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                String sourceFileName = "FishData.json";
                File destinationFile = new File(getFilesDir(), sourceFileName);

                try {
                    // Import the selected file to the app's internal storage
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    OutputStream outputStream = new FileOutputStream(destinationFile);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }

                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();

                    // Display success message or perform further actions
                    error.setTextColor(Color.GREEN);
                    error.setText("Import Successful! Restart App to See Changes");

                } catch (IOException e) {
                    e.printStackTrace();
                    // Display error message or handle the import failure
                    error.setTextColor(Color.RED);
                    error.setText("Import Failed! Check App Permissions in the Settings");
                }
            }
        }
    }
}