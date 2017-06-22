package com.regrex.dailyJokes.widgets;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.regrex.dailyJokes.R;

/**
 * Created by janisharali on 19/08/16.
 */
@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderCard {

    private static int count;

    private String s;

    public TinderCard() {

    }

    public  TinderCard(String s) {
        this.s = s;

    }

    @View(R.id.profileImageView)
    private TextView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    @Click(R.id.profileImageView)
    private void onClick() {
        Log.d("DEBUG", "profileImageView");
    }

    @Resolve
    private void onResolve() {
        nameAgeTxt.setText("Name " + count++);
        profileImageView.setText(s);
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("DEBUG", "onSwipedOut");
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("DEBUG", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {
        Log.d("DEBUG", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("DEBUG", "onSwipeOutState");
    }
}
