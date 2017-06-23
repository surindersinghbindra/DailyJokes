package com.regrex.dailyJokes.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.regrex.dailyJokes.R;
import com.regrex.dailyJokes.model.JokeSingle;
import com.regrex.dailyJokes.widgets.TinderCard2;
import com.regrex.dailyJokes.binding.RecyclerViewBinding;
import com.regrex.dailyJokes.model.JokeDetail;

import java.util.ArrayList;

public class ReadJokeActivity extends AppCompatActivity implements RecyclerViewBinding.OnClick {

    private RecyclerView recyclerView;
    private static final String TAG = "ActivityTinder";

    @BindView(R.id.swipeView)
    private SwipePlaceHolderView mSwipView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_joke);
        ButterKnifeLite.bind(this);
      /*  recyclerView = (RecyclerView) findViewById(R.id.rvChutkaleCAtegories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jokes");

        Query query = myRef.orderByChild("categoryId").equalTo(getIntent().getIntExtra("CATEGORY", 1));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<JokeSingle> jokeDetailsList = new ArrayList<>();

                if (dataSnapshot.exists()) {

                    // mSwipView.disableTouchSwipe();

                    mSwipView.getBuilder()
                            .setDisplayViewCount(55)
                            .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                            .setSwipeDecor(new SwipeDecor()
                         //                                  .setMarginTop(300)
                       // .setMarginLeft(100)
                      // .setViewGravity(Gravity.TOP)
                                    .setPaddingTop(20)
                                    .setRelativeScale(0.01f)
                                    //.setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                                  //  .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view)
                                    );
/*
                    mSwipView.getBuilder()
//                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_VERTICAL)
                            .setDisplayViewCount(55)
                            .setIsUndoEnabled(true)
                            .setWidthSwipeDistFactor(15)
                            .setHeightSwipeDistFactor(20)
                            .setSwipeDecor(new SwipeDecor()
//                        .setMarginTop(300)
//                        .setMarginLeft(100)
//                        .setViewGravity(Gravity.TOP)
                                    .setPaddingTop(20)
                                    .setRelativeScale(0.01f)
                                    .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                                    .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));*/

                    for (DataSnapshot joke : dataSnapshot.getChildren()) {
                        JokeSingle jokeDetail = joke.getValue(JokeSingle.class);
                        jokeDetailsList.add(jokeDetail);
                        mSwipView.addView(new TinderCard2(jokeDetail.jokeContent));

                    }
                    jokeDetailsList.size();



               /*     mSwipView.addView(new TinderCard())
                            .addView(new TinderCard())
                            .addView(new TinderCard())
                            .addView(new TinderCard())
                            .addView(new TinderCard())
                            .addView(new TinderCard())
                            .addView(new TinderCard())
                            .addView(new TinderCard())
                            .addView(new TinderCard())
                            .addView(new TinderCard());*/

                   // recyclerView.setAdapter(new RecyclerViewBinding<JokeDetail>(jokeDetailsList, BR.jokeDetail, R.layout.single_item_detail, ReadJokeActivity.this));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void myOnClick(int s) {

    }
}
