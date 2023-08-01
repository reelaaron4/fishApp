package com.example.taskmanager;

import android.content.res.Resources;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class customXAxisLabelFormatter implements IAxisValueFormatter {

    private ArrayList<String> mValues;
    private String lastLabel = "";

    // Constructor that specifies axis labels.
    public customXAxisLabelFormatter(ArrayList<String> values) {
        if (values != null) {
            mValues = values;
        } else {
            mValues = new ArrayList<>();
        }
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        //set axis text size
        // axis.setTextSize(dpToPx(5));
        axis.setTextSize(dpToPx(5));
        int index = Math.round(value);

        if (index < 0 || index >= mValues.size())
            return "";

        String currentLabel = mValues.get(index);
        if (currentLabel.equals(lastLabel)) {
            // Return an empty string to skip displaying this label
            return "";
        } else {
            // Update the lastLabel with the current label
            lastLabel = currentLabel;
            return currentLabel;
        }
    }

    // Helper method to convert dp to pixels
    private int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
