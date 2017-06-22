package com.regrex.awesomejokes.di.modules;

import android.support.annotation.NonNull;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.regrex.awesomejokes.ApiManager.ApiService;
import com.regrex.awesomejokes.AppConstants;
import com.regrex.awesomejokes.BuildConfig;
import com.regrex.awesomejokes.HttpLoggingInterceptor;
import com.regrex.awesomejokes.di.qualifiers.Interceptor;


import java.text.DateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Surinder on 12/02/16.
 */
@Module
public class ApiModule {

    public ApiModule() {
    }

    @Provides
    @Singleton
    public GsonConverterFactory provideGsonConverter() {

        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat(DateFormat.LONG)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();

        return GsonConverterFactory.create(gson);
    }


    @Provides
    @Singleton
    public CallAdapter.Factory provideCallAdapter() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //debug fields
        // add logging as last interceptor
       /* if (interceptors != null && !interceptors.isEmpty()) {
            for (okhttp3.Interceptor interceptor : interceptors) {
                if ("StethoInterceptor".equals(interceptor.getClass().getSimpleName())) {
                    builder.networkInterceptors().add(interceptor);
                } else {
                    builder.interceptors().add(interceptor);
                }
            }
        }*/
        builder.connectTimeout(180, TimeUnit.SECONDS);
        builder.readTimeout(180, TimeUnit.SECONDS);
        builder.addInterceptor(logging);

        return builder.build();
    }


    @Provides
    @Singleton
    ApiService provideJokes(OkHttpClient client, GsonConverterFactory converter, CallAdapter.Factory callAdapter) {
        Retrofit retrofit = buildRetrofit(client, converter, callAdapter, AppConstants.APP_BASE_URL);

        return retrofit.create(ApiService.class);
    }
    @NonNull
    private Retrofit buildRetrofit(OkHttpClient client, Converter.Factory converter, CallAdapter.Factory callAdapter, String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(callAdapter)
                .addConverterFactory(converter)
                .build();
    }
}
