package com.example.taskmanager;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class dateTextWatcher implements TextWatcher {

    private static final int MAX_LENGTH = 7;
    private EditText editText;
    private boolean isFormatting;

    public dateTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No action needed before text changes.
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // No action needed while text changes.
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isFormatting) {
            return;
        }

        isFormatting = true;
        String input = s.toString();

        // Remove any existing slashes and digits from the input
        String formattedDate = input.replaceAll("[^0-9]", "");

        // Insert slash after the 2nd character
        if (formattedDate.length() > 2) {
            formattedDate = formattedDate.substring(0, 2) + "/" + formattedDate.substring(2);
        }

        // Trim the string to the maximum length
        if (formattedDate.length() > MAX_LENGTH) {
            formattedDate = formattedDate.substring(0, MAX_LENGTH);
        }

        editText.setText(formattedDate);
        editText.setSelection(formattedDate.length());
        isFormatting = false;
    }
}
