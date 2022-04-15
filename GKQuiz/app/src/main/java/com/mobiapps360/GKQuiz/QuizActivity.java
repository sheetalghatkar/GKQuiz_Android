
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
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

    public static ArrayList<QuestionItem> questionItemDataArray;

    public static SharedPreferences sharedPreferences = null;
    public static final String myPreferences = "myPref";
    public static final String soundQuizActivity = "soundQuizActivityKey";

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
        //  ArrayList<QuestionItem>  questionItemDataList =  (ArrayList<QuestionItem>) getIntent().getSerializableExtra("QuizArray");
        //System.out.println("Inside QuizActivity****" + getIntent().getStringExtra("screenTitle"));

        questionItemModelArray = Constant.arrayXyz;   //.toArray(questionItemModelArray);


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

//        System.out.println("My Width : "+ m_width);
//        System.out.println("My Height : "+ (int) (1080 / Resources.getSystem().getDisplayMetrics().density));


       /* Glide.with(this).load(R.drawable.clock_learn).into(imgViewHomeGif);

        MobileAds.initialize(getApplicationContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adViewBannerMainActivity);
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
        });*/

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
                                stopPlayerSound();
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
                        QuizActivity.super.onBackPressed();
                    }
                }
                return true;
            }
        });
        //------------------------------------------

    }

    public static void reloadRecycleView(int updatePosition) {
        //System.out.println("updatePosition***" + updatePosition);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!((Constant.arrayXyz.size() - 1) == updatePosition)) {
                        recyclerView.scrollToPosition(updatePosition + 1);
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

    public void stopPlayerSound() {
        if (player != null) {
            player.stop();
        }
    }

    public void soundHomeBtnClicked(View v) {
       // Log.v("soundHomeBtnClicked", "i m inside homeBtnClicked");
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
                stopPlayerSound();
            }
        }
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
}