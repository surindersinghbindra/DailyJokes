package com.regrex.awesomejokes.di.components;




import com.regrex.awesomejokes.di.modules.ActivityModule;
import com.regrex.awesomejokes.di.scopes.PerActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {


}
