package com.rileylundquist.liftsense;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rileylundquist.liftsense.database.WorkoutDBHelper;

import java.util.ArrayList;
import java.util.List;


public class WorkoutActivity extends  ListActivity {

    public static final String EXTRA_EXERCISE = "com.rileylundquist.liftsense.EXERCISE";
    private List<String> workoutItems;
    private AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getApplicationContext());
    private WorkoutDBHelper dbHelper;
    private SQLiteDatabase db;
    private boolean dbReady;

    private class DBTask extends AsyncTask {

        @Override
        protected Boolean doInBackground(Object[] params) {
            dbHelper = new WorkoutDBHelper(getApplicationContext());
            db = dbHelper.getWritableDatabase();
            return Boolean.TRUE;
        }

        protected void onPostExecute(Boolean ready) {
            dbReady = ready.booleanValue();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        workoutItems = new ArrayList<String>();
        for (int j=1; j<12; j++)
            workoutItems.add("exercise " + Integer.toString(j));

        setupAdapter();

        setupLongClick();

        new DBTask().execute();
    }

    private void setupAdapter() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.workout_list_item, R.id.exercise_name, workoutItems);
        setListAdapter(arrayAdapter);
    }

    private void setupLongClick() {
        /*this.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                dialogBuilder.setItems(R.array.workout_item_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0)
                            editExercise(position);
                        else
                            goToCameraForExercise(position);
                    }
                });
                return true;
            }
        });*/
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        goToCameraForExercise(position);
    }

    protected void editExercise(int position) {

    }

    protected void goToCameraForExercise(int position) {
        String selectedExercise = (String) getListView().getItemAtPosition(position);

        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(EXTRA_EXERCISE, selectedExercise);
        startActivity(intent);
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
