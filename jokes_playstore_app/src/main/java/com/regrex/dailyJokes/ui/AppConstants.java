package com.regrex.dailyJokes.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by surinder on 21-Jun-17.
 */

class AppConstants {

    static DatabaseReference getFireBaseRefrence() {
        return FirebaseDatabase.getInstance().getReference();
    }

    static DatabaseReference getUserRefrence() {
        return getFireBaseRefrence().child("users");
    }

    static DatabaseReference getCategoryRef() {
        return getFireBaseRefrence().child("joke_categories");
    }

    static DatabaseReference getJokeRef() {
        return getFireBaseRefrence().child("jokes");
    }
}
