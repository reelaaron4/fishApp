package com.example.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class fishAdapter extends ArrayAdapter<Fish> {
    public fishAdapter(Context context, ArrayList<Fish> fish) {
        super(context, 0, fish);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fish fish = getItem(getCount() - position - 1);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fish_item, parent, false);
        }

        TextView fishSpecies = convertView.findViewById(R.id.fish_species);
        TextView fishLength = convertView.findViewById(R.id.fish_length);
        TextView fishBait = convertView.findViewById(R.id.fish_bait);
        TextView fishMisc = convertView.findViewById(R.id.fish_misc);
        TextView fishMisc2 = convertView.findViewById(R.id.fish_misc2);
        TextView fishMisc3 = convertView.findViewById(R.id.fish_misc3);
        TextView fishWeight = convertView.findViewById(R.id.fish_weight);
        TextView fishTemp = convertView.findViewById(R.id.fish_temp);
        TextView fishDate = convertView.findViewById(R.id.fish_date);

        TextView fishId = convertView.findViewById(R.id.fish_id);
        fishId.setText(String.format("%d", fish.getId() + 1));
        fishSpecies.setText(fish.getSpecies());
        fishLength.setText(String.format(Locale.getDefault(), "%.1f in", fish.getLength()));
        fishBait.setText(fish.getBait());

        if (fish.getMisc() != null) {
            fishMisc.setText(fish.getMisc());
        } else {
            //fishMisc.setVisibility(View.INVISIBLE);
            fishMisc.setText("");
        }

        if (fish.getMisc2() != null) {
            fishMisc2.setText(fish.getMisc2());
        } else {
            //fishMisc.setVisibility(View.INVISIBLE);
            fishMisc2.setText("");
        }

        if (fish.getMisc3() != null) {
            fishMisc3.setText(fish.getMisc3());
        } else {
            //fishMisc.setVisibility(View.INVISIBLE);
            fishMisc3.setText("");
        }

        if (fish.getWeight() > 0.0) {
            fishWeight.setText(String.format(Locale.getDefault(), "%.1f lbs", fish.getWeight()));
        } else {
            //fishWeight.setVisibility(View.INVISIBLE);
            fishWeight.setText("");
        }

        if (fish.getTemp() > 0.0) {
            fishTemp.setText(String.format(Locale.getDefault(), "%.1f °F", fish.getTemp()));
        } else {
            //fishTemp.setVisibility(View.INVISIBLE);
            fishTemp.setText("");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a\n", Locale.getDefault());

        fishDate.setText(dateFormat.format(fish.getDate()));

        return convertView;
    }
}
