package com.regrex.jokesupload.model;

/**
 * Created by surinder on 19-Jun-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.regrex.jokesupload.db.AppDatabase;


@Table(database = AppDatabase.class)
public class Datum {

    @Column
    @SerializedName("dislikes")
    @Expose
    private String dislikes;

    @Column
    @SerializedName("likes")
    @Expose
    private String likes;

    @Column
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private String id;

    @Column
    @SerializedName("category")
    @Expose
    private String category;

    @Column
    @SerializedName("content")
    @Expose
    private String content;

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}