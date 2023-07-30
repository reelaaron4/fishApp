/*
package com.example.taskmanager;

import android.graphics.Canvas;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class customBarChartRenderer extends BarChartRenderer {

    public customBarChartRenderer(BarLineChartBase<?> chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super((BarDataProvider) chart, animator, viewPortHandler);
    }

    @Override
    public void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        // Set a static bar width
        mBarWidth = mChart.getBarData().getBarWidth() * 0.7f; // Adjust the scaling factor as needed

        super.drawDataSet(c, dataSet, index);
    }

    @Override
    public float[] getTransformedValues(BarData data, IBarDataSet dataSet, int dataSetIndex, float animatorPhase) {
        // Override the getTransformedValues method to prevent scaling in Y-axis
        return super.getTransformedValues(data, dataSet, dataSetIndex, 0f);
    }
}

*/
