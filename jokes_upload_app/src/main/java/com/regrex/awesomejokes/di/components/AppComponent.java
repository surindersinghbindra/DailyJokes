package com.regrex.awesomejokes.di.components;

import com.regrex.awesomejokes.ApiManager.ApiService;
import com.regrex.awesomejokes.MainActivity;
import com.regrex.awesomejokes.di.modules.ApiModule;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, ApplicationModule.class})
public interface AppComponent {
    void inject(MainActivity homeActivity);

    ApiService api();


    //  SharedPrefsHelper sharedPrefsHelper();
}
