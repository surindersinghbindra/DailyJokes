package com.regrex.jokesupload.di.qualifiers;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by thuongle on 12/02/16.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Interceptor {
}
