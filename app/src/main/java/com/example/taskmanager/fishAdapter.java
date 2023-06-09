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
        TextView fishWeather = convertView.findViewById(R.id.fish_weather);
        TextView fishWeight = convertView.findViewById(R.id.fish_weight);
        TextView fishTemp = convertView.findViewById(R.id.fish_temp);
        TextView fishDate = convertView.findViewById(R.id.fish_date);

        TextView fishId = convertView.findViewById(R.id.fish_id);
        fishId.setText(String.format("%d", fish.getId() + 1));
        fishSpecies.setText(fish.getSpecies());
        fishLength.setText(String.format(Locale.getDefault(), "%.1f in", fish.getLength()));
        fishBait.setText(fish.getBait());

        if (fish.getWeather() != null) {
            fishWeather.setText(fish.getWeather());
        } else {
            fishWeather.setVisibility(View.GONE);
        }

        if (fish.getWeight() > 0.0) {
            fishWeight.setText(String.format(Locale.getDefault(), "%.1f lbs", fish.getWeight()));
        } else {
            fishWeight.setVisibility(View.GONE);
        }

        if (fish.getTemp() > 0.0) {
            fishTemp.setText(String.format(Locale.getDefault(), "%.1f °F", fish.getTemp()));
        } else {
            fishTemp.setVisibility(View.GONE);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a\n", Locale.getDefault());

        fishDate.setText(dateFormat.format(fish.getDate()));

        return convertView;
    }
}
