package com.example.taskmanager;

import android.content.res.Resources;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class customXAxisLabelFormatter implements IAxisValueFormatter {

    private ArrayList<String> mValues;

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
        int index = Math.round(value);

        if (index < 0 || index >= mValues.size())
            return "";
        //set axis text size
        axis.setTextSize(dpToPx(5));

       // mValues.get(index)
        return mValues.get(index);
    }

    // Helper method to convert dp to pixels
    private int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}