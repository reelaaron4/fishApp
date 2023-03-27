package com.example.taskmanager;

import com.github.mikephil.charting.data.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class scatterCalc {
    public static int[][] countOccurrences(List<Entry> entries) {
        Map<Entry, Integer> occurrencesMap = new HashMap<>();

        // Count occurrences of each entry
        for (Entry entry : entries) {
            boolean found = false;
            for (Entry key : occurrencesMap.keySet()) {
                if (key.getX() == entry.getX() && key.getY() == entry.getY()) {
                    int count = occurrencesMap.get(key);
                    occurrencesMap.put(key, count + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                occurrencesMap.put(entry, 1);
            }
        }

        // Convert map to 2D array
        int[][] occurrences = new int[occurrencesMap.size()][2];
        int i = 0;
        for (Entry entry : occurrencesMap.keySet()) {
            occurrences[i][0] = entries.indexOf(entry);
            occurrences[i][1] = occurrencesMap.get(entry) ;
            i++;
        }

        return occurrences;
    }
}
