package com.regrex.dailyJokes;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by surinder on 02-Jun-17.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FlowManager.init(this);
    }
}
