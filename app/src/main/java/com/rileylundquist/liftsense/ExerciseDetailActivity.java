package com.rileylundquist.liftsense;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * An activity representing a single Exercise detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ExerciseListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ExerciseDetailFragment}.
 */
public class ExerciseDetailActivity extends Activity {

    public static final String EXTRA_EXERCISE = "com.rileylundquist.liftsense.EXERCISE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ExerciseDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ExerciseDetailFragment.ARG_ITEM_ID));
            ExerciseDetailFragment fragment = new ExerciseDetailFragment();
            fragment.setArguments(arguments);
//            getFragmentManager().beginTransaction()
//                    .add(R.id.exercise_detail_container, fragment)
//                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ExerciseListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        //intent.putExtra(EXTRA_EXERCISE, mItem.content);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                EditText weightField = (EditText) findViewById(R.id.weight_field);
                weightField.setText(Float.toString(data.getFloatExtra("result", 4)));
            }
            if (resultCode == Activity.RESULT_CANCELED)
                ;
        }
    }
}
