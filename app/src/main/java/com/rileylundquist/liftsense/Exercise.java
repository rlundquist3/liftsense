package com.rileylundquist.liftsense;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by riley on 3/6/16.
 */
public class Exercise {

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;
    private int sets;
    private int reps;
    private float weight;
    private String name;
    private boolean done;

    public Exercise(String name) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            this.setUid(user.getUid());
        this.setName(name);
        this.setSets(0);
        this.setReps(0);
        this.setWeight(0);
    }

    public Exercise(String name, int sets, int reps, float weight) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            this.setUid(user.getUid());
        this.setName(name);
        this.setSets(sets);
        this.setReps(reps);
        this.setWeight(weight);
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("sets", sets);
        result.put("reps", reps);
        result.put("weight", weight);
        result.put("done", done);

        return result;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDone() {
        this.done = true;
    }

    public void setNotDone(){
        this.done = false;
    }

    public void toggleDone() {
        this.done = !this.done;
    }

    public boolean isDone() {
        return this.done;
    }
}
