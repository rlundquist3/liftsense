package com.rileylundquist.liftsense.database;

import android.provider.BaseColumns;

/**
 * Created by riley on 4/3/16.
 */
public final class WorkoutContract {
    public WorkoutContract() {}

    public static abstract class WorkoutEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercises";
        //public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SETS = "sets";
        public static final String COLUMN_NAME_REPS = "reps";
        public static final String COLUMN_NAME_WEIGHT = "weight";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WorkoutEntry.TABLE_NAME + " (" +
                    WorkoutEntry._ID + " INTEGER PRIMARY KEY," +
                    //WorkoutEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    WorkoutEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    WorkoutEntry.COLUMN_NAME_SETS + TEXT_TYPE + COMMA_SEP +
                    WorkoutEntry.COLUMN_NAME_REPS + TEXT_TYPE + COMMA_SEP +
                    WorkoutEntry.COLUMN_NAME_WEIGHT + TEXT_TYPE + " )";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WorkoutEntry.TABLE_NAME;
}