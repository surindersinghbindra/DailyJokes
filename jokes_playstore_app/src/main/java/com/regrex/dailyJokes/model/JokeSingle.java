package com.regrex.dailyJokes.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.regrex.dailyJokes.db.AppDatabase;


/**
 * Created by surinder on 22-Jun-17.
 */

@Table(database = AppDatabase.class)
public class JokeSingle extends BaseModel {


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

    public int getAlreadyRead() {
        return alreadyRead;
    }

    public void setAlreadyRead(int alreadyRead) {
        this.alreadyRead = alreadyRead;
    }

    @Column(defaultValue = "0")
    public int alreadyRead;

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

    public JokeSingle() {
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
}
