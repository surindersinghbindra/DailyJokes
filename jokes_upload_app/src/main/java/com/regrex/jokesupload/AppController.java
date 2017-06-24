package com.regrex.jokesupload;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.regrex.jokesupload.di.components.AppComponent;
import com.regrex.jokesupload.di.components.ApplicationModule;

import com.regrex.jokesupload.di.components.DaggerAppComponent;
import com.regrex.jokesupload.di.modules.ApiModule;


public class AppController extends Application {


    public static SharedPreferences sharedPreferencesCompat;

    public static SharedPreferences.Editor getSharedPrefEditor() {
        return sharedPreferencesCompat.edit();
    }

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);


        FlowManager.init(this);

        sharedPreferencesCompat = getSharedPreferences("APP_PREF", MODE_PRIVATE);

        component = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule())
                .build();


    }

    public AppComponent getComponent() {
        return component;
    }
}

