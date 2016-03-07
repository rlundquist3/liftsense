package com.rileylundquist.liftsense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by riley on 3/6/16.
 */
public class ExerciseListAdapter extends ArrayAdapter<Exercise> {
    private static class ViewHolder {
        TextView name;
        TextView home;
    }

    public ExerciseListAdapter(Context context, ArrayList<Exercise> exercises) {
        super(context, R.layout.workout_list_item, exercises);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Exercise exercise = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.workout_list_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.exercise_name);
            viewHolder.home = (TextView) convertView.findViewById(R.id.exercise_detail_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(exercise.getName());
        viewHolder.home.setText(exercise.getSets() + ", " + exercise.getReps() + ", " + exercise.getWeight());
        // Return the completed view to render on screen
        return convertView;
    }
}
