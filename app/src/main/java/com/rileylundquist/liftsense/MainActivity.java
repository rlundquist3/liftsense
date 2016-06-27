package com.rileylundquist.liftsense;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ExerciseListFragment.Callbacks, ProfileFragment.OnFragmentInteractionListener {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        setFabWorkout();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

//            goToWorkout();
            goToCamera();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_workout) {
            goToWorkout();
        } else if (id == R.id.nav_profile) {
            goToProfile();
        } else if (id == R.id.nav_camera) {
            goToCamera();
        } else if (id == R.id.nav_manage) {
            goToSettings();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToWorkout() {
        ExerciseListFragment listFragment = new ExerciseListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, listFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        fab.hide();
    }

    public void goToProfile() {
//        Snackbar.make(findViewById(R.id.fragment_container), "go to profile", Snackbar.LENGTH_LONG).show();
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, profileFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        fab.hide();
    }

    public void goToSettings() {
        Snackbar.make(findViewById(R.id.fragment_container), "go to settings", Snackbar.LENGTH_LONG).show();
//        ExerciseListFragment listFragment = new ExerciseListFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, listFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }

    /**
     * Callback method from {@link ExerciseListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        ExerciseDetailFragment detailFragment = new ExerciseDetailFragment();
        Bundle args = new Bundle();
        args.putString(ExerciseDetailFragment.ARG_ITEM_ID, id);
        detailFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        setFabCamera();
    }

    public void goToCamera() {
//        Snackbar.make(findViewById(R.id.fragment_container), "go to camera", Snackbar.LENGTH_LONG).show();
        Intent intent = new Intent(this, CameraActivity.class);
        //intent.putExtra(EXTRA_EXERCISE, mItem.content);
        startActivityForResult(intent, 1);
    }

    public void setFabCamera() {
        fab.setImageResource(R.drawable.ic_camera_alt__white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCamera();
            }
        });
        fab.show();
    }

    public void setFabWorkout() {
        fab.setImageResource(R.drawable.ic_barbell_white_24px);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWorkout();
            }
        });
        fab.show();
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
