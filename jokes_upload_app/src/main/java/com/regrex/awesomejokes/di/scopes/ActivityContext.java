package com.regrex.awesomejokes.di.scopes;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ActivityContext
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {
}
