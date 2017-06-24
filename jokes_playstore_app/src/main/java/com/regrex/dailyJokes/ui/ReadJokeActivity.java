package com.regrex.dailyJokes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.butterknifelite.annotations.OnClick;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.regrex.dailyJokes.R;
import com.regrex.dailyJokes.binding.RecyclerViewBinding;
import com.regrex.dailyJokes.model.CategorySingle;
import com.regrex.dailyJokes.model.JokeSingle;
import com.regrex.dailyJokes.model.JokeSingle_Table;
import com.regrex.dailyJokes.utils.DataBaseHelper;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadJokeActivity extends AppCompatActivity implements RecyclerViewBinding.OnClick {

    private static final String TAG = "ActivityTinder";
    private RecyclerView recyclerView;
    private List<JokeSingle> jokeSinglesList = new ArrayList<>();
    @BindView(R.id.tvJokeContent)
    private TextView tvJokeContent;
    private int jokeMumber = 0;

    @BindView(R.id.ll_Previous)
    private LinearLayout ll_Previous;

    @BindView(R.id.ll_Next)
    private LinearLayout ll_Next;

    @BindView(R.id.ll_Share)
    private LinearLayout ll_Share;

    @BindView(R.id.ll_disLike)
    private LinearLayout ll_disLike;
    @BindView(R.id.ll_Like)
    private LinearLayout ll_Like;


    @BindView(R.id.toolbar)
    private Toolbar toolbar;


    @BindView(R.id.sbLike)
    private ShineButton sbLike;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_joke);
        ButterKnifeLite.bind(this);

        CategorySingle categorySingle = getIntent().getParcelableExtra("CATEGORY_OBJECT");
        toolbar.setTitle(categorySingle.categoryEnglish);
        toolbar.setSubtitle(categorySingle.categoryNameHindi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
      /*  recyclerView = (RecyclerView) findViewById(R.id.rvChutkaleCAtegories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.getReadableDatabase();

        } catch (IOException e) {
            e.printStackTrace();
        }

        FlowManager.getDatabaseForTable(JokeSingle.class)
                .beginTransactionAsync(new QueryTransaction.Builder<>(
                        SQLite.select()
                                .from(JokeSingle.class)
                                .where(JokeSingle_Table.categoryId.eq(categorySingle.categoryId)).and(JokeSingle_Table.likes.eq(0)))
                        .queryResult(new QueryTransaction.QueryResultCallback<JokeSingle>() {
                            @Override
                            public void onQueryResult(QueryTransaction<JokeSingle> transaction, @NonNull CursorResult<JokeSingle> tResult) {

                                tvJokeContent.setText(tResult.getItem(0).getJokeContent());
                                jokeSinglesList.addAll(tResult.toList());

                            }
                        }).build())
                .build().execute();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jokes");

        Query query = myRef.orderByChild("categoryId").equalTo(getIntent().getIntExtra("CATEGORY", 1)).limitToFirst(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<JokeSingle> jokeDetailsList = new ArrayList<>();

                if (dataSnapshot.exists()) {

                    // mSwipView.disableTouchSwipe();

                  /*  mSwipView.getBuilder()
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
                            );*/


                    for (DataSnapshot joke : dataSnapshot.getChildren()) {
                        JokeSingle jokeDetail = joke.getValue(JokeSingle.class);
                        jokeDetailsList.add(jokeDetail);
                        //  mSwipView.addView(new TinderCard2(jokeDetail.jokeContent));

                    }
                    jokeDetailsList.size();


                    // recyclerView.setAdapter(new RecyclerViewBinding<JokeDetail>(jokeDetailsList, BR.jokeDetail, R.layout.single_item_detail, ReadJokeActivity.this));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @OnClick(R.id.ll_Share)
    public void onClickShare() {
        animateOverShoot(ll_Share);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //   String shareBody = jokeSinglesList.get(jokeMumber).getJokeContent() + "\n Hey! I found an amazing app for Jokes.Download here\n" + "http://market.android.com/details?id=" + ReadJokeActivity.this.getPackageName();
        String shareBody = jokeSinglesList.get(jokeMumber).getJokeContent() + "\n Hey! I found an amazing app for Jokes.Download here\n" + "https://play.google.com/store/apps/details?id=" + ReadJokeActivity.this.getPackageName();

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Pharmacognize");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @OnClick(R.id.ll_Next)
    public void onClickNext() {
        sbLike.setChecked(false);
        if (jokeMumber < jokeSinglesList.size()) {
            jokeMumber++;
            animateOverShoot(ll_Next);
            tvJokeContent.setText(jokeSinglesList.get(jokeMumber).getJokeContent());
        }


    }

    @OnClick(R.id.ll_Previous)
    public void onClickPrevious() {
        sbLike.setChecked(false);
        if (jokeMumber > 0) {
            jokeMumber--;
            animateOverShoot(ll_Previous);
            tvJokeContent.setText(jokeSinglesList.get(jokeMumber).getJokeContent());
        } else {

        }

    }

    @OnClick(R.id.ll_Like)
    public void onClickLike() {
        animateOverShoot(ll_Like);

    }

    @OnClick(R.id.ll_disLike)
    public void onClickDisLike() {
        animateOverShoot(ll_disLike);

    }

    @Override
    public void myOnClick(Object s) {

    }

    private void animateOverShoot(LinearLayout view) {
        view.getChildAt(0).setScaleX(0);
        view.getChildAt(0).setScaleY(0);
        view.getChildAt(0).animate()
                .scaleX(1)
                .scaleY(1)
                .alpha(1)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(150)
                .start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
