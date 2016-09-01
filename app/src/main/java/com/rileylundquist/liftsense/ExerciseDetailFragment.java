package com.rileylundquist.liftsense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rileylundquist.liftsense.WorkoutContent;

import org.w3c.dom.Text;

/**
 * A fragment representing a single Exercise detail screen.
 * This fragment is either contained in a {@link ExerciseListActivity}
 * in two-pane mode (on tablets) or a {@link ExerciseDetailActivity}
 * on handsets.
 */
public class ExerciseDetailFragment extends Fragment implements View.OnClickListener {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private WorkoutContent.Exercise mItem;

    private Button saveButton;

    private DatabaseReference mDatabase;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExerciseDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments().containsKey(ARG_ITEM_ID)) {
////             Load the dummy content specified by the fragment
////             arguments. In a real-world scenario, use a Loader
////             to load content from a content provider.
//            mItem = WorkoutContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
//        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exercise_detail, container, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
//        String unit = sharedPref.getString("pref_units", "lbs");
//        ((TextView) rootView.findViewById(R.id.unit_text)).setText(unit);

        if (mItem != null) {
            Log.d("TEST", mItem.content);
            TextView name = (TextView) getActivity().findViewById(R.id.exercise_detail_name);
            name.setText(mItem.content);
//            getActivity().getActionBar().setTitle(mItem.content);
        }

//        saveButton = (Button) rootView.findViewById(R.id.save_button);
//        saveButton.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.save_button:
//                FragmentManager manager = getFragmentManager();
//                manager.popBackStack();
//                break;
                   }
    }

    @Override
    public void onPause() {
        //To finish after other db stuff--i.e. pulling workout data
        //update workout data here
        super.onPause();
    }
}
