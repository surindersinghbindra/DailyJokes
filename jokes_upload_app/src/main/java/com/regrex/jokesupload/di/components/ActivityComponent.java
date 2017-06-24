package com.regrex.jokesupload.di.components;




import com.regrex.jokesupload.di.modules.ActivityModule;
import com.regrex.jokesupload.di.scopes.PerActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {


}
