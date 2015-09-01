package com.rileylundquist.liftsense;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class WorkoutActivity extends  ListActivity {

    private List<String> workoutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        workoutItems = new ArrayList<String>();
        for (int j=1; j<12; j++)
            workoutItems.add("exercise " + Integer.toString(j));

        setupAdapter();
    }

    private void setupAdapter() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.workout_list_item, R.id.listItem, workoutItems);
        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        String selectedItem = (String) getListView().getItemAtPosition(position);

        Toast.makeText(getApplicationContext(), selectedItem + " clicked", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
