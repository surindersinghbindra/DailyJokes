package com.regrex.dailyJokes.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by surinder on 19-Jun-17.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "AppDatabase";

    public static final int VERSION = 1;
}
