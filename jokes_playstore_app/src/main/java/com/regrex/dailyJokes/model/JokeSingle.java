package com.regrex.dailyJokes.model;

/**
 * Created by surinder on 22-Jun-17.
 */

public class JokeSingle {


    public JokeSingle(int jokeId, int categoryId, String jokeContent, int languageOfJoke, int likes, int dislikes, long addedOn) {
        this.jokeId = jokeId;
        this.categoryId = categoryId;
        this.jokeContent = jokeContent;
        this.languageOfJoke = languageOfJoke;
        this.likes = likes;
        this.dislikes = dislikes;
        this.addedOn = addedOn;
        this.addedOn = System.currentTimeMillis();
    }

    public int jokeId;
    public int categoryId;
    public String jokeContent;
    public int languageOfJoke;
    public int likes, dislikes;
    public long addedOn;


    public JokeSingle() {
    }
}
