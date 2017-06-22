package com.regrex.awesomejokes.ApiManager;

import com.regrex.awesomejokes.model.JokeModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by surinder on 17-Jun-17.
 */

public interface ApiService {

    @GET("/apps/app_whatsupjokes/api/friends")
    Call<JokeModel> getJokes();


}
