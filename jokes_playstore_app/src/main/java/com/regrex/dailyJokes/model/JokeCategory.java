package com.regrex.dailyJokes.model;

import android.databinding.BaseObservable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by surinder on 02-Jun-17.
 */
@IgnoreExtraProperties
public class JokeCategory extends BaseObservable {


    private int fid;
    private int id;
    private String name;
    private int languageId;
    private int languageName;


    public JokeCategory(int id, String name, int languageId, int languageName) {
        this.id = id;
        this.name = name;
        this.languageId = languageId;
        this.languageName = languageName;
    }


    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }


    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getLanguageName() {
        return languageName;
    }

    public void setLanguageName(int languageName) {
        this.languageName = languageName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public JokeCategory() {
    }
}
