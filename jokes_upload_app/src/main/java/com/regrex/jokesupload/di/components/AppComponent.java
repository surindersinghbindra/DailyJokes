package com.regrex.jokesupload.di.components;

import com.regrex.jokesupload.ApiManager.ApiService;
import com.regrex.jokesupload.MainActivity;
import com.regrex.jokesupload.di.modules.ApiModule;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, ApplicationModule.class})
public interface AppComponent {
    void inject(MainActivity homeActivity);

    ApiService api();


    //  SharedPrefsHelper sharedPrefsHelper();
}
