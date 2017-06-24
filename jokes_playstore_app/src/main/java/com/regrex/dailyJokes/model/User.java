package com.regrex.dailyJokes.model;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public long lastUpdatedDbTimeStamp;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, long lastUpdatedDbTimeStamp) {
        this.username = username;
        this.email = email;
        this.lastUpdatedDbTimeStamp = lastUpdatedDbTimeStamp;
    }

}
// [END blog_user_class]
