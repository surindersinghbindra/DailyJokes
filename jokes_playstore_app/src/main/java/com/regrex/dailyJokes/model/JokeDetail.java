package com.regrex.dailyJokes.model;

import android.databinding.BaseObservable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by surinder on 02-Jun-17.
 */
@IgnoreExtraProperties
public class JokeDetail extends BaseObservable {

    private int id, main_id, fav;
    private String jokeContent;
    private Date date;
    private long updatedOn;
    private String authorName;
    private String authorId;
    private int likesCount;
    private Map<String, Boolean> whoLikes = new HashMap<>();

    public JokeDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMain_id() {
        return main_id;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public String getJokeContent() {
        return jokeContent;
    }

    public void setJokeContent(String jokeContent) {
        this.jokeContent = jokeContent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public JokeDetail(int id, int main_id, String jokeContent, int fav) {
        this.id = id;
        this.main_id = main_id;
        this.jokeContent = jokeContent;
        this.fav = fav;
        this.date = new Date();

    }
}
