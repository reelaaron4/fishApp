package com.example.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class tasksAdapter extends ArrayAdapter<Task>
{
    public tasksAdapter(Context context, ArrayList<Task> tasks)
    {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(getCount() - position - 1);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        TextView taskId = convertView.findViewById(R.id.task_id); // get the TextView for the task id
        TextView taskName = convertView.findViewById(R.id.task_name);
        TextView taskDesc = convertView.findViewById(R.id.task_description);

        taskId.setText(String.valueOf(task.getId() +1)); // set the task id in the TextView
        taskName.setText(task.getName());
        taskDesc.setText(task.getDescription());

        return convertView;
    }
}
