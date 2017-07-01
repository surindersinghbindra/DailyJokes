package com.regrex.dailyJokes.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.regrex.dailyJokes.R;
import com.regrex.dailyJokes.databinding.ActivitySplashBinding;
import com.regrex.dailyJokes.model.User;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import tyrantgit.explosionfield.ExplosionField;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {


    public static final String TAG = SplashActivity.class.getSimpleName();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private static final int RC_SIGN_IN = 9001;
    private ActivitySplashBinding activitySplashBinding;
    private Runnable runnable;
    private Handler handler;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private DatabaseReference mDatabase;

    private GoogleApiClient mGoogleApiClient;
    private ExplosionField mExplosionField;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear(); // clearing it : do not emit after destroy
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


/*        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, SplashActivity.this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        mDatabase = FirebaseDatabase.getInstance().getReference();


        activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mExplosionField = ExplosionField.attach2Window(this);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            activitySplashBinding.btnSignIn.setVisibility(View.VISIBLE);
        } else {
            activitySplashBinding.btnSignIn.setVisibility(View.GONE);

            runnable = new Runnable() {
                @Override
                public void run() {
                    callMainActivity();
                }


            };

            handler = new Handler();
            handler.postDelayed(runnable, 4500);

        }

        disposables.add(getObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()));


        activitySplashBinding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }

    private Observable<? extends Long> getObservable() {
        return Observable.interval(0, 220, TimeUnit.MILLISECONDS);
    }

    private DisposableObserver<Long> getObserver() {
        return new DisposableObserver<Long>() {

            @Override
            public void onNext(Long value) {
                int temp = Integer.valueOf(value + "");
                switch (temp) {
                    case 1:
                        animatemy(activitySplashBinding.smiley3);
                        break;
                    case 2:
                        animatemy(activitySplashBinding.smiley11);
                        break;
                    case 3:
                        animatemy(activitySplashBinding.smiley8);
                        break;
                    case 4:
                        animatemy(activitySplashBinding.smiley10);
                        break;
                    case 5:
                        animatemy(activitySplashBinding.smiley5);
                        break;
                    case 6:
                        animatemy(activitySplashBinding.smiley13);
                        break;
                    case 7:
                        animatemy(activitySplashBinding.smiley4);
                        break;
                    case 8:
                        animatemy(activitySplashBinding.smiley9);
                        break;
                    case 9:
                        animatemy(activitySplashBinding.smiley1);
                        break;
                    case 10:
                        animatemy(activitySplashBinding.smiley7);
                        break;
                    case 11:
                        animatemy(activitySplashBinding.smiley2);
                        break;
                    case 12:
                        animatemy(activitySplashBinding.smiley12);
                        break;
                    case 13:
                        animatemy(activitySplashBinding.smiley6);
                        break;
                    case 14:
                        mExplosionField.explode(activitySplashBinding.appLogo);
                        break;

                    default:
                        // animatemy(activitySplashBinding.smiley7);
                        break;

                }

                Log.d(TAG, " onNext : value : " + value);
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, " onComplete");
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 5;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }

    private void animatemy(View view) {
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.25, 40);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);
    }

    private void callMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));

        finish();
        overridePendingTransition(R.anim.zoom_exit,0);

    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                Snackbar.make(activitySplashBinding.layoutMain, R.string.sign_in_failed, Snackbar.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]


    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            hideProgressDialog();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            AppConstants.getUserRefrence().child(user.getUid()).setValue(new User(user.getDisplayName(), user.getEmail(), System.currentTimeMillis() + ""));
                            callMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SplashActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]


    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                    }
                });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // something left here for
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loadAds() {

    }
}
