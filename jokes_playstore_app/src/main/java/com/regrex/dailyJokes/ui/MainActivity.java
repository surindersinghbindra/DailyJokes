package com.regrex.dailyJokes.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.regrex.dailyJokes.AppController;
import com.regrex.dailyJokes.BuildConfig;
import com.regrex.dailyJokes.R;
import com.regrex.dailyJokes.binding.RecyclerViewBinding;
import com.regrex.dailyJokes.model.CategorySingle;
import com.regrex.dailyJokes.model.JokeCategory;
import com.regrex.dailyJokes.utils.DataBaseHelper;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewBinding.OnClick {
    private RecyclerView recyclerView;
    private ImageView tvProfileImage;
    private TextView tvDisplayName, tvEmail;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        adView = (AdView) findViewById(R.id.adView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        tvProfileImage = (ImageView) header.findViewById(R.id.tvProfileImage);
        tvDisplayName = (TextView) header.findViewById(R.id.tvDisplayName);
        tvEmail = (TextView) header.findViewById(R.id.tvEmail);

        Picasso.with(MainActivity.this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(tvProfileImage);
        tvDisplayName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        tvEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.rvChutkaleCAtegories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jokes_category");
        myRef.keepSynced(true);

       /* try {
           // DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
          //  dataBaseHelper.opendatabase();

           // SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
            //Cursor cursor = db.rawQuery("SELECT * FROM main_joke", null);
            List<JokeCategory> jokeDetailsList = new ArrayList<>();
        *//*    if (cursor.moveToFirst()) {
                do {
                    //  JokeCategory testBean = new JokeCategory(cursor.getInt(0), cursor.getString(1));
                    //  myRef.push().setValue(testBean);
                    //      jokeDetailsList.add(testBean);

                } while (cursor.moveToNext());

            }

            jokeDetailsList.size();
            cursor.close();
            db.close();*//*
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Query query = myRef.orderByChild("categoryId");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<CategorySingle> jokeDetailsList = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot joke : dataSnapshot.getChildren()) {
                        CategorySingle jokeDetail = joke.getValue(CategorySingle.class);
                        jokeDetailsList.add(jokeDetail);
                    }
                    jokeDetailsList.size();
                    recyclerView.setAdapter(new RecyclerViewBinding<CategorySingle>(jokeDetailsList, com.regrex.dailyJokes.BR.jokeCategory, R.layout.single_item_category, MainActivity.this));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        loadAds();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            //   String shareBody = jokeSinglesList.get(jokeMumber).getJokeContent() + "\n Hey! I found an amazing app for Jokes.Download here\n" + "http://market.android.com/details?id=" + ReadJokeActivity.this.getPackageName();
            String shareBody = "Hey! I found an amazing app for Jokes.\nDownload here\n" + "https://play.google.com/store/apps/details?id=" + this.getPackageName();

            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        } else if (id == R.id.nav_send) {

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"regrexapps@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " feedback from " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            // i.putExtra(Intent.EXTRA_TEXT, "body of email");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void myOnClick(Object s) {
        if (s instanceof CategorySingle) {
            Intent intent = new Intent(MainActivity.this, ReadJokeActivity.class);
            intent.putExtra("CATEGORY_OBJECT", (CategorySingle) s);
            startActivity(intent);
        }

    }

    @Override
    public void loadAds() {
        //adView.setAdSize(AdSize.BANNER);

        AdRequest adRequest = AppController.commonAdRequest();

        if (BuildConfig.DEBUG) {
            //adView.setAdUnitId(getString(R.string.banner_ad_unit_id_debug));

        } else {

            // adView.setAdUnitId(getString(R.string.banner_joke_content_release));
        }

        adView.loadAd(adRequest);
    }
}
