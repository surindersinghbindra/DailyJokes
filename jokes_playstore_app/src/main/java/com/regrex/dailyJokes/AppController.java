package com.regrex.dailyJokes;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.FirebaseDatabase;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.regrex.dailyJokes.utils.DataBaseHelper;

import java.io.IOException;

/**
 * Created by surinder on 02-Jun-17.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        FlowManager.init(this);
        if (BuildConfig.DEBUG) {
            MobileAds.initialize(this, getString(R.string.admob_app_id_debug));
            Log.e("DEBUG1", "BUILD_CONFIG");
        } else {
            MobileAds.initialize(this, getString(R.string.admob_app_id_release));
            Log.e("DEBUG1", "RELEASE");
        }

    }

    public static AdRequest commonAdRequest() {

        //  return new AdRequest.Builder().build();
        // moto g4 plus
        if (BuildConfig.DEBUG) {
            Log.e("DEBUG2", "BUILD_CONFIG");
            return new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("41D20A27D81DEE80A9F2C26610668F6E").build();

        } else {
            Log.e("DEBUG2", "RELEASE");
            return new AdRequest.Builder().build();

        }


        // moto g2
        // return new AdRequest.Builder().addTestDevice("41D20A27D81DEE80A9F2C26610668F6E").build();

    }
}
