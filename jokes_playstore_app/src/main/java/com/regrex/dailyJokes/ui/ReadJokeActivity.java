package com.regrex.dailyJokes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.regrex.dailyJokes.AppController;
import com.regrex.dailyJokes.BuildConfig;
import com.regrex.dailyJokes.R;
import com.regrex.dailyJokes.binding.RecyclerViewBinding;
import com.regrex.dailyJokes.db.AppDatabase;
import com.regrex.dailyJokes.model.CategorySingle;
import com.regrex.dailyJokes.model.JokeSingle;
import com.regrex.dailyJokes.model.JokeSingle_Table;
import com.regrex.dailyJokes.utils.OnSwipeTouchListener;
import com.regrex.dailyJokes.widgets.AppTextView;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.List;

public class ReadJokeActivity extends BaseActivity implements RecyclerViewBinding.OnClick {


    private InterstitialAd interstitial;
    private static final String TAG = "ActivityTinder";
    private RecyclerView recyclerView;
    private List<JokeSingle> jokeSinglesList = new ArrayList<>();
    @BindView(R.id.tvJokeContent)
    private TextSwitcher tvJokeContent;
    private int jokeMumber = 0;


    @BindView(R.id.adView)
    private AdView adView;
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

    @BindView(R.id.scrollView)
    private ScrollView scrollView;


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

        loadAds();
       /* try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.getReadableDatabase();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        TextView textView = new TextView(this);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        textView.setTextSize(20f);


        TextView textView1 = new TextView(this);
        textView1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        textView1.setTextSize(20f);

        tvJokeContent.addView(new AppTextView(this));
        tvJokeContent.addView(new AppTextView(this));
        tvJokeContent.setInAnimation(this, R.anim.fadein);
        tvJokeContent.setOutAnimation(this, R.anim.fadeout);
        scrollView.setSmoothScrollingEnabled(false);
        scrollView.setOnTouchListener(new OnSwipeTouchListener(ReadJokeActivity.this) {
            public void onSwipeTop() {
               // Toast.makeText(ReadJokeActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
              //  Toast.makeText(ReadJokeActivity.this, "right", Toast.LENGTH_SHORT).show();
                onClickPrevious();
            }

            public void onSwipeLeft() {
              //  Toast.makeText(ReadJokeActivity.this, "left", Toast.LENGTH_SHORT).show();
                onClickNext();
            }

            public void onSwipeBottom() {
              //  Toast.makeText(ReadJokeActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

        FlowManager.getDatabaseForTable(JokeSingle.class)
                .beginTransactionAsync(new QueryTransaction.Builder<>(
                        SQLite.select()
                                .from(JokeSingle.class)
                                .where(JokeSingle_Table.categoryId.eq(categorySingle.categoryId)).and(JokeSingle_Table.likes.eq(0)).and(OperatorGroup.clause().or(JokeSingle_Table.alreadyRead.isNull())).or(JokeSingle_Table.alreadyRead.eq(0)))
                        .queryResult(new QueryTransaction.QueryResultCallback<JokeSingle>() {
                            @Override
                            public void onQueryResult(QueryTransaction<JokeSingle> transaction, @NonNull CursorResult<JokeSingle> tResult) {
                                try {
                                    if (tResult.getCount() > 0) {
                                        tvJokeContent.setText(tResult.getItem(0).getJokeContent());
                                        jokeSinglesList.addAll(tResult.toList());
                                    }
                                } catch (Exception e) {

                                } finally {
                                    tResult.close();
                                }


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
        String shareBody = jokeSinglesList.get(jokeMumber).getJokeContent() + "\n Hey! I found an amazing app for Jokes.\nDownload here\n" + "https://play.google.com/store/apps/details?id=" + ReadJokeActivity.this.getPackageName();

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Pharmacognize");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @OnClick(R.id.ll_Next)
    public void onClickNext() {
        sbLike.setChecked(false);
        if (jokeMumber < jokeSinglesList.size()) {
            scrollView.smoothScrollTo(0,0);
            JokeSingle jokeSingle = jokeSinglesList.get(jokeMumber);
            jokeSingle.setAlreadyRead(1);
            FlowManager.getModelAdapter(JokeSingle.class).save(jokeSingle);
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
            scrollView.smoothScrollTo(0,0);
            animateOverShoot(ll_Previous);
            tvJokeContent.setText(jokeSinglesList.get(jokeMumber).getJokeContent());
        } else {

        }

    }

    @OnClick(R.id.sbLike)
    public void onClickSbLike() {
        FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                // something here

                //   FlowManager.getModelAdapter(JokeSingle.class).save(model);

                JokeSingle jokeSingle = jokeSinglesList.get(jokeMumber);
                jokeSingle.setLikes(1);
                jokeSingle.setAlreadyRead(1);

                jokeSingle.save(databaseWrapper); // use wrapper (from BaseModel)
            }
        });
    }

    @OnClick(R.id.ll_Like)
    public void onClickLike() {
        //animateOverShoot(ll_Like);

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
    public void onBackPressed() {
        super.onBackPressed();
        if (interstitial != null && interstitial.isLoaded()) {
            interstitial.show();
        }
        // finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void loadAds() {

        //adView.setAdSize(AdSize.BANNER);

        AdRequest adRequest = AppController.commonAdRequest();
        interstitial = new InterstitialAd(this);
        if (BuildConfig.DEBUG) {
            Log.e("DEBUG", "interstitial_DEBUG");
            //adView.setAdUnitId(getString(R.string.banner_ad_unit_id_debug));
            interstitial.setAdUnitId(getString(R.string.interstitial_ad_unit_id_debug));
        } else {
            interstitial.setAdUnitId(getString(R.string.interstitial_joke_content_release));
            // adView.setAdUnitId(getString(R.string.banner_joke_content_release));
            Log.e("DEBUG", "interstitial_RElease");
        }
        interstitial.loadAd(adRequest);
        adView.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.e("LISTNER", i + "");
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
    }
}
