package com.regrex.jokesupload.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.regrex.jokesupload.db.AppDatabase;

/**
 * Created by surinder on 22-Jun-17.
 */

@Table(database = AppDatabase.class)
public class JokeSingle {


    public JokeSingle(int jokeId, int categoryId, String jokeContent, int languageOfJoke, int likes, int dislikes, String addedOn) {
        this.jokeId = jokeId;
        this.categoryId = categoryId;
        this.jokeContent = jokeContent;
        this.languageOfJoke = languageOfJoke;
        this.likes = likes;
        this.dislikes = dislikes;
        this.addedOn = addedOn;
        this.addedOn = System.currentTimeMillis() + "";
    }


    public int getJokeId() {
        return jokeId;
    }

    public void setJokeId(int jokeId) {
        this.jokeId = jokeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getJokeContent() {
        return jokeContent;
    }

    public void setJokeContent(String jokeContent) {
        this.jokeContent = jokeContent;
    }

    public int getLanguageOfJoke() {
        return languageOfJoke;
    }

    public void setLanguageOfJoke(int languageOfJoke) {
        this.languageOfJoke = languageOfJoke;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    @PrimaryKey
    @Unique
    public int jokeId;
    @Column
    public int categoryId;
    @Column
    public String jokeContent;
    @Column
    public int languageOfJoke;
    @Column(defaultValue = "0")
    public int likes;
    @Column(defaultValue = "0")
    public int dislikes;
    @Column
    public String addedOn;


    public JokeSingle() {
    }
}
