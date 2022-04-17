
package com.mobiapps360.GKQuiz;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {


    // private ActivityMainBinding binding;
    private ImageView imgViewHomeGif;
    private ImageButton btnImgHomeSound;
    private ImageButton btnQuizBack;
    private ImageView imgViewWallBg;
    TextView txtViewQuizTitle;
    ImageView homeBoardImage;
    static QuizAdapter quizAdapter;
    MediaPlayer player;
    ArrayList<QuestionItem> questionItemModelArray;
    static QuizRecycleView recyclerView;
    static com.mobiapps360.GKQuiz.CircleLayoutManager circleLayoutManager;
    private AdView mAdView;
    AdRequest adRequest;
    ImageView imgVwQuizLoader;
    View viewQuizLoader;
    public static ArrayList<QuestionItem> questionItemDataArray;
    private InterstitialAd mInterstitialAd;
    Handler handlerNoConnection;
    int previousIndex = 0;

    public static SharedPreferences sharedPreferences = null;
    public static final String myPreferences = "myPref";
    public static final String soundQuizActivity = "soundQuizActivityKey";
    int clickCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //  binding = ActivityMainBinding.inflate(getLayoutInflater());
        //  setContentView(binding.getRoot());
        //  Log.v("xxx", "Inside getSoundFlag loop----");

        //  imgViewHomeGif = findViewById(R.id.imgViewHomeGif);
        btnImgHomeSound = findViewById(R.id.btnSoundQuiz);
        imgViewWallBg = findViewById(R.id.quizWallImage);
        btnQuizBack = findViewById(R.id.btnQuizBack);
        txtViewQuizTitle = findViewById(R.id.txtViewQuizTitle);
        txtViewQuizTitle.setText(getIntent().getStringExtra("screenTitle"));
        imgVwQuizLoader = findViewById(R.id.imgVwQuizLoader);
        viewQuizLoader = findViewById(R.id.viewLoaderQuizBg);
        Glide.with(this).load(R.drawable.loader).into(imgVwQuizLoader);
        //  ArrayList<QuestionItem>  questionItemDataList =  (ArrayList<QuestionItem>) getIntent().getSerializableExtra("QuizArray");
        //System.out.println("Inside QuizActivity****" + getIntent().getStringExtra("screenTitle"));

        questionItemModelArray = Constant.arrayXyz;   //.toArray(questionItemModelArray);
        handlerNoConnection = new Handler();


        quizAdapter = new QuizAdapter(this);
        quizAdapter.setListMenuItem(questionItemModelArray);

        recyclerView = (QuizRecycleView) findViewById(R.id.recycler);
        com.mobiapps360.GKQuiz.CircleLayoutManager circleLayoutManager = new com.mobiapps360.GKQuiz.CircleLayoutManager(this);
        recyclerView.addOnScrollListener(new com.mobiapps360.GKQuiz.CenterScrollListener());


        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(circleLayoutManager);
        recyclerView.setAdapter(quizAdapter);

        recyclerView.setItemAnimator(null);

        recyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int offset = recyclerView.computeHorizontalScrollOffset();
                        if (offset % recyclerView.getWidth() == 0) {
                            int position = offset / recyclerView.getWidth();
                            System.out.println("position*****"+position);
//                            int idSwipeImg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/swipe", null, null);

                        }
                    }
                });




        MobileAds.initialize(getApplicationContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adViewBannerQuizActivity);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
                // Toast.makeText(MainActivity.this,"ad loaded",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                System.out.println("Show error####"+adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        // System.out.println("Quiz----****" + btnImgHomeSound);
        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(soundQuizActivity)) {
            Boolean getSoundFlag = sharedPreferences.getBoolean(soundQuizActivity, false);
            if (getSoundFlag == true) {
                // playSound();
                btnImgHomeSound.setImageResource(R.mipmap.sound_on);
            } else {
                btnImgHomeSound.setImageResource(R.mipmap.sound_off);
            }
        } else {
            editor.putBoolean(soundQuizActivity, true);
            editor.commit();
            btnImgHomeSound.setImageResource(R.mipmap.sound_on);
        }
        // guessTimeDataArray = parseGuessTimeArray("guess_time");


        btnImgHomeSound.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        // System.out.println("*******&&&&");
                        // v.animate().cancel();
                        if (sharedPreferences.contains(soundQuizActivity)) {
                            Boolean getSoundFlag = sharedPreferences.getBoolean(soundQuizActivity, false);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            getSoundFlag = !getSoundFlag;
                            editor.putBoolean(soundQuizActivity, getSoundFlag);
                            editor.commit();
                            if (getSoundFlag == true) {
                                btnImgHomeSound.setImageResource(R.mipmap.sound_on);
                            } else {
                                btnImgHomeSound.setImageResource(R.mipmap.sound_off);
                            }
                        }
                    }
                }
                return true;
            }
        });
        //------------------------------------------
        btnQuizBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        handlerNoConnection.removeCallbacksAndMessages(null);
                        QuizActivity.super.onBackPressed();
                    }
                }
                return true;
            }
        });
        //------------------------------------------

    }

    void reloadRecycleView(int updatePosition, boolean isShowNextIndex) {
        //System.out.println("updatePosition***" + updatePosition);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isShowNextIndex) {
                        if (!((Constant.arrayXyz.size() - 1) == updatePosition)) {
                            recyclerView.scrollToPosition(updatePosition + 1);
                        }
                    }
                    if (previousIndex != updatePosition) {
                        System.out.println("clickCount updated"+clickCount);
                        previousIndex = updatePosition;
                        clickCount = clickCount + 1;
                        if (clickCount > Constant.showInterstialAdAfterCount) {
                            clickCount = 0;
                            showInterstitialAds(false);
                        }
                    }
                    //  recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, new RecyclerView.State(), updatePosition+1);
//                    recyclerView.smoothScrollToPosition(updatePosition+1);
                    //  recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, new RecyclerView.State(), updatePosition);
//                    recyclerView.scrollToPosition(updatePosition+1);

                } catch (Exception e) {

                }
            }
        }, 1500);
    }

    void playSoundOptionClick(String soundName) {
        if (MainActivity.sharedPreferences.getBoolean(soundQuizActivity, false)) {
            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.GKQuiz:raw/" + soundName, null, null);
            //   player.setVolume(0.0f, 0.0f);
            //System.out.println("playSound clicked ---------" + idSoundBg);

            try {
                player = MediaPlayer.create(getBaseContext(), idSoundBg);
            } catch (Exception e) {
                Log.e("Music Exception", "catch button click sound play");
            }

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {
                    player.start();
                }
            });

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.release();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        handlerNoConnection.removeCallbacksAndMessages(null);
        super.onBackPressed();
    }
    protected void onRestart() {
        super.onRestart();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    protected void onStop() {
        super.onStop();

    }


    public void onActivityPaused(Activity activity) {
        System.out.println("onActivityPaused------");
        if (player != null) {
            player.release();
        }
    }

    public void onActivityStopped(Activity activity) {
        System.out.println("onActivityStopped------");
        if (player != null) {
            player.release();
        }
    }

    //Show interstitial Ads
    public void showHideLoader(boolean adFlag) {
        if (adFlag) {
            imgVwQuizLoader.setVisibility(View.VISIBLE);
            viewQuizLoader.setVisibility(View.VISIBLE);
        } else {
            imgVwQuizLoader.setVisibility(View.INVISIBLE);
            viewQuizLoader.setVisibility(View.INVISIBLE);
        }
    }

    public void showInterstitialAds(Boolean fromHome) {
        System.out.println("Inside showInterstitialAds---");
        showHideLoader(true);
        if (isOnline()) {
            InterstitialAd.load(this, Constant.INTERSTITIAL_ID, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd;
                    mInterstitialAd.show(QuizActivity.this);

                    // Log.i(TAG, "onAdLoaded");
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when fullscreen content is dismissed.
                            Log.i("TAG", "The ad was dismissed.");
                            if (fromHome) {
                                Log.i("playCrad", "The ad was dismissed---if");
                                QuizActivity.super.onBackPressed();
                                showHideLoader(false);
                            } else {
                                Log.i("playCrad", "The ad was dismissed-----else.");
                                showHideLoader(false);
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when fullscreen content failed to show.
                            showHideLoader(false);
                            Log.d("TAG", "The ad failed to show.");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            //showHideLoader(false);
                            // Called when fullscreen content is shown.
                            // Make sure to set your reference to null so you don't
                            // show it a second time.
                            mInterstitialAd = null;
                            // Log.d("TAG", "The ad was shown.");
                        }
                    });

                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
                    showHideLoader(false);
                    mInterstitialAd = null;
                }
            });
        } else {
            handlerNoConnection.postDelayed(new Runnable() {
                public void run() {
                    showHideLoader(false);
                }
            }, Constant.loaderWhenNoInternet);
        }
    }
}