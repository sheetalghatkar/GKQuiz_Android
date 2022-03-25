package com.mobiapps360.GKQuiz;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    // private ActivityMainBinding binding;
    private ImageView imgViewHomeGif;
    private ImageButton btnImgSetting;
    private ImageButton btnImgHomeSound;
    private View viewSettingBg;
    private ImageView imgViewsettingBg;
    private ImageView imgViewBottom;
    private ImageView imgViewWallBg;
    ImageView homeBoardImage;
    MediaPlayer player;
    RelativeLayout optionsRelativeLayout;
    RotateAnimation rC;
    int getwidth = 100;
    //    private AdView mAdView;
//    AdRequest adRequest;
//    public static ArrayList<GuessTimeItem>  guessTimeDataArray;
//    public static ArrayList<GuessTimeItem>  guessTimeFinalArray;
    public static SharedPreferences sharedPreferences = null;
    public static final String myPreferences = "myPref";
    public static final String soundHomeActivity = "soundHomeActivityKey";
    // for all the FABs
    FloatingActionButton fab_setting, fab_img_rateUs, fab_img_shareApp, fab_img_otherApps, fab_img_privacyPolicy;
    // with FABs
    TextView fab_txt_rateUs, fab_txt_shareApp, fab_txt_otherApps, fab_txt_privacyPolicy;
    // to check whether sub FAB buttons are visible or not.
    Boolean isAllFabsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  binding = ActivityMainBinding.inflate(getLayoutInflater());
        //  setContentView(binding.getRoot());
        Log.v("xxx", "Inside getSoundFlag loop----");

        //  imgViewHomeGif = findViewById(R.id.imgViewHomeGif);
        btnImgHomeSound = findViewById(R.id.btnSoundHomeScreen);
        homeBoardImage = findViewById(R.id.homeBoardImage);
        btnImgSetting = findViewById(R.id.btnSetting);
        imgViewWallBg = findViewById(R.id.mainWallImage);
        viewSettingBg = findViewById(R.id.viewSettingBg);
        imgViewsettingBg = findViewById(R.id.imgViewSettingBg);
        optionsRelativeLayout = (RelativeLayout) findViewById(R.id.cont);
        System.out.println("Inside --"+Resources.getSystem().getDisplayMetrics().widthPixels);
        Display display = getWindowManager().getDefaultDisplay();
        Point m_size = new Point();
        display.getSize(m_size);
        int m_width = m_size.x;
        int m_height = m_size.y;
        getwidth = (int) (1080 / Resources.getSystem().getDisplayMetrics().density);
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


        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(soundHomeActivity)) {
            Boolean getSoundFlag = sharedPreferences.getBoolean(soundHomeActivity, false);
            if (getSoundFlag == true) {
                playSound();
                btnImgHomeSound.setImageResource(R.mipmap.sound_on);
            } else {
                btnImgHomeSound.setImageResource(R.mipmap.sound_off);
            }
        } else {
            editor.putBoolean(soundHomeActivity, true);
            editor.commit();
            btnImgHomeSound.setImageResource(R.mipmap.sound_on);
            playSound();
        }
        // guessTimeDataArray = parseGuessTimeArray("guess_time");

//        View v = findViewById(R.id.cont);
        //TextView text1 = findViewById(R.id.text1);
        TextView text1 =  new TextView(this);
        TextView text2 =  new TextView(this);
        TextView text3 =  new TextView(this);
        TextView text4 =  new TextView(this);
        TextView text5 =  new TextView(this);
        TextView text6 =  new TextView(this);

        int xAdd = 300;
        int yAdd = 300;

         RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(100, 100);
        text6.setBackgroundColor(getResources().getColor(R.color.pink_color));
        int durationMillis = 10000;
        int numViews = 5;
        float angleDeg = 5 * 360.0f / 6 - 90.0f;
        float angleRad = (float) (angleDeg * Math.PI / 180.0f);
        lp6.leftMargin = (int)(300 * (float) Math.cos(angleRad)) + xAdd;
        lp6.topMargin = (int)(300 * (float) Math.sin(angleRad)) + yAdd;
//        text5.setX(300 * (float) Math.cos(angleRad));
//        text5.setY(300 * (float) Math.sin(angleRad));
        text6.setText("M");
        text6.setLayoutParams(lp6);
        RotateAnimation t6 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t6.setDuration(durationMillis);
        t6.setInterpolator(new LinearInterpolator());
        t6.setRepeatMode(Animation.RESTART);
        t6.setRepeatCount(Animation.INFINITE);
        optionsRelativeLayout.addView(text6, lp6);
        text6.startAnimation(t6);



        RelativeLayout.LayoutParams lp5 = new RelativeLayout.LayoutParams(100, 100);
        text5.setBackgroundColor(getResources().getColor(R.color.red_done));
         angleDeg = 0 * 360.0f / 6 - 90.0f;
         angleRad = (float) (angleDeg * Math.PI / 180.0f);
        lp5.leftMargin = (int)(300 * (float) Math.cos(angleRad)) + xAdd;
        lp5.topMargin = (int)(300 * (float) Math.sin(angleRad)) + yAdd;
//        text5.setX(300 * (float) Math.cos(angleRad));
//        text5.setY(300 * (float) Math.sin(angleRad));
        text5.setText("M");
        text5.setLayoutParams(lp5);
        RotateAnimation t5 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t5.setDuration(durationMillis);
        t5.setInterpolator(new LinearInterpolator());
        t5.setRepeatMode(Animation.RESTART);
        t5.setRepeatCount(Animation.INFINITE);
        optionsRelativeLayout.addView(text5, lp5);
         text5.startAnimation(t5);




        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100, 100);
        text1.setBackgroundColor(getResources().getColor(R.color.green_done));
         angleDeg = 1 * 360.0f / 6 - 90.0f;
         angleRad = (float) (angleDeg * Math.PI / 180.0f);
//        text1.setX(300 * (float) Math.cos(angleRad));
//        text1.setY(300 * (float) Math.sin(angleRad));
        lp.leftMargin = (int)(300 * (float) Math.cos(angleRad)) + xAdd;
        lp.topMargin = (int)(300 * (float) Math.sin(angleRad)) + yAdd;
//        text1.setTranslationX(300 * (float) Math.cos(angleRad));
//        text1.setTranslationY(300 * (float) Math.sin(angleRad));
        text1.setLayoutParams(lp);
        text1.setText("Y");
        RotateAnimation t1 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t1.setDuration(durationMillis);
        t1.setInterpolator(new LinearInterpolator());
        t1.setRepeatMode(Animation.RESTART);
        t1.setRepeatCount(Animation.INFINITE);
        optionsRelativeLayout.addView(text1, lp);
        text1.startAnimation(t1);


        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(100, 100);
        text2.setBackgroundColor(getResources().getColor(R.color.pink_color));
        angleDeg = 2 * 360.0f / 6 - 90.0f;
        angleRad = (float) (angleDeg * Math.PI / 180.0f);
//        text2.setX(300 * (float) Math.cos(angleRad));
//        text2.setY(300 * (float) Math.sin(angleRad));
        lp2.leftMargin = (int)(300 * (float) Math.cos(angleRad)) + xAdd;
        lp2.topMargin = (int)(300 * (float) Math.sin(angleRad)) + yAdd;

//        text2.setTranslationX(300 * (float) Math.cos(angleRad));
//        text2.setTranslationY(300 * (float) Math.sin(angleRad));

        text2.setLayoutParams(lp2);
        text2.setText("K");
        RotateAnimation t2 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t2.setDuration(durationMillis);
        t2.setInterpolator(new LinearInterpolator());
        t2.setRepeatMode(Animation.RESTART);
        t2.setRepeatCount(Animation.INFINITE);
        optionsRelativeLayout.addView(text2, lp2);
         text2.startAnimation(t2);

        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(100, 100);
        text3.setBackgroundColor(getResources().getColor(R.color.white));
        angleDeg = 3 * 360.0f / 6 - 90.0f;
        angleRad = (float) (angleDeg * Math.PI / 180.0f);
//        text3.setX(300 * (float) Math.cos(angleRad));
//        text3.setY(300 * (float) Math.sin(angleRad));
        lp3.leftMargin = (int)(300 * (float) Math.cos(angleRad)) + xAdd;
        lp3.topMargin = (int)(300 * (float) Math.sin(angleRad)) + yAdd;
//        text3.setTranslationX(300 * (float) Math.cos(angleRad));
//        text3.setTranslationY(300 * (float) Math.sin(angleRad));
        text3.setLayoutParams(lp3);
        text3.setText("L");

        RotateAnimation t3 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t3.setDuration(durationMillis);
        t3.setInterpolator(new LinearInterpolator());
        t3.setRepeatMode(Animation.RESTART);
        t3.setRepeatCount(Animation.INFINITE);
        optionsRelativeLayout.addView(text3, lp3);
         text3.startAnimation(t3);


        RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(100, 100);
        text4.setBackgroundColor(getResources().getColor(R.color.black));
        angleDeg = 4 * 360.0f / 6 - 90.0f;
        angleRad = (float) (angleDeg * Math.PI / 180.0f);

//        text4.setX(300 * (float) Math.cos(angleRad));
//        text4.setY(300 * (float) Math.sin(angleRad));
        lp4.leftMargin = (int)(300 * (float) Math.cos(angleRad)) + xAdd;
        lp4.topMargin = (int)(300 * (float) Math.sin(angleRad)) + yAdd;
//        text4.setTranslationX(300 * (float) Math.cos(angleRad));
//        text4.setTranslationY(300 * (float) Math.sin(angleRad));
        text4.setLayoutParams(lp4);
        text4.setText("F");

        RotateAnimation t4 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t4.setDuration(durationMillis);
        t4.setInterpolator(new LinearInterpolator());
        t4.setRepeatMode(Animation.RESTART);
        t4.setRepeatCount(Animation.INFINITE);
        optionsRelativeLayout.addView(text4, lp4);
        text4.startAnimation(t4);
        text4.setTextColor(getResources().getColor(R.color.white));
        text4.startAnimation(t4);

       /* text2.setBackgroundColor(getResources().getColor(R.color.blue_color));
        angleDeg = 5 * 360.0f / 6 - 90.0f;
        angleRad = (float) (angleDeg * Math.PI / 180.0f);
        lp.leftMargin = (int)(10 * (float) Math.cos(angleRad));
        lp.topMargin = (int)(10 * (float) Math.sin(angleRad));
        text2.setVisibility(View.VISIBLE);
        text2.setLayoutParams(lp);

        v.addView(text2, lp);

        RotateAnimation t2 = new RotateAnimation(60, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t2.setDuration(durationMillis);
        t2.setInterpolator(new LinearInterpolator());
        t2.setRepeatMode(Animation.RESTART);
        t2.setRepeatCount(Animation.INFINITE);
      //  text2.startAnimation(t2);*/

     /*   RotateAnimation t2 = new RotateAnimation(60, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t2.setDuration(durationMillis);
        t2.setInterpolator(new LinearInterpolator());
        t2.setRepeatMode(Animation.RESTART);
        t2.setRepeatCount(Animation.INFINITE);
        text2.startAnimation(t2);

        RotateAnimation t3 = new RotateAnimation(120, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t3.setDuration(durationMillis);
        t3.setInterpolator(new LinearInterpolator());
        t3.setRepeatMode(Animation.RESTART);
        t3.setRepeatCount(Animation.INFINITE);
        text3.startAnimation(t3);

        RotateAnimation t4 = new RotateAnimation(180, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t4.setDuration(durationMillis);
        t4.setInterpolator(new LinearInterpolator());
        t4.setRepeatMode(Animation.RESTART);
        t4.setRepeatCount(Animation.INFINITE);
        text4.startAnimation(t4);

        RotateAnimation t5 = new RotateAnimation(240, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        t5.setDuration(durationMillis);
        t5.setInterpolator(new LinearInterpolator());
        t5.setRepeatMode(Animation.RESTART);
        t5.setRepeatCount(Animation.INFINITE);
        text5.startAnimation(t5);*/

        rC = new RotateAnimation(360, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rC.setDuration(durationMillis);
        rC.setInterpolator(new LinearInterpolator());
        rC.setRepeatMode(Animation.RESTART);
        rC.setRepeatCount(Animation.INFINITE);
        optionsRelativeLayout.startAnimation(rC);
//


        imgViewsettingBg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("inside imgViewSettingBg$$$$$$$$$$$$$$$$$$$$$$");

            }
        });

        viewSettingBg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("inside viewSettingBg######## ");
                if (isAllFabsVisible) {
                    fab_img_rateUs.hide();
                    fab_img_shareApp.hide();
                    fab_img_otherApps.hide();
                    fab_img_privacyPolicy.hide();
                    fab_txt_rateUs.setVisibility(View.GONE);
                    fab_txt_shareApp.setVisibility(View.GONE);
                    fab_txt_otherApps.setVisibility(View.GONE);
                    fab_txt_privacyPolicy.setVisibility(View.GONE);
                    isAllFabsVisible = false;
                    imgViewsettingBg.setVisibility(View.INVISIBLE);
                    viewSettingBg.setVisibility(View.INVISIBLE);
                }
            }
        });
        // This FAB button is the Parent
        fab_setting = findViewById(R.id.fab_btn_setting);
        // FAB button
        fab_img_rateUs = findViewById(R.id.fab_img_rateUs);
        fab_img_shareApp = findViewById(R.id.fab_img_shareApp);
        fab_img_otherApps = findViewById(R.id.fab_img_otherApps);
        fab_img_privacyPolicy = findViewById(R.id.fab_img_privacyPolicy);

        // Also register the action name text, of all the FABs.
        fab_txt_rateUs = findViewById(R.id.fab_txt_rateUs);
        fab_txt_shareApp = findViewById(R.id.fab_txt_shareApp);
        fab_txt_otherApps = findViewById(R.id.fab_txt_otherApps);
        fab_txt_privacyPolicy = findViewById(R.id.fab_txt_privacyPolicy);
        // Now set all the FABs and all the action name
        // texts as GONE
        fab_img_rateUs.setVisibility(View.GONE);
        fab_img_shareApp.setVisibility(View.GONE);
        fab_img_otherApps.setVisibility(View.GONE);
        fab_img_privacyPolicy.setVisibility(View.GONE);
        fab_txt_rateUs.setVisibility(View.GONE);
        fab_txt_shareApp.setVisibility(View.GONE);
        fab_txt_otherApps.setVisibility(View.GONE);
        fab_txt_privacyPolicy.setVisibility(View.GONE);
        imgViewsettingBg.setVisibility(View.INVISIBLE);
        viewSettingBg.setVisibility(View.INVISIBLE);
        ViewCompat.setTranslationZ(viewSettingBg, 10);
        ViewCompat.setTranslationZ(imgViewsettingBg, 11);
        ViewCompat.setTranslationZ(fab_txt_rateUs, 15);
        ViewCompat.setTranslationZ(fab_txt_shareApp, 15);
        ViewCompat.setTranslationZ(fab_txt_otherApps, 15);
        ViewCompat.setTranslationZ(fab_txt_privacyPolicy, 15);


        fab_setting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((FloatingActionButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((FloatingActionButton) v).setAlpha((float) 1.0);
                        if (!isAllFabsVisible) {
                            fab_txt_rateUs.setTextColor(getResources().getColor(R.color.white));
                            fab_txt_shareApp.setTextColor(getResources().getColor(R.color.white));
                            fab_txt_otherApps.setTextColor(getResources().getColor(R.color.white));
                            fab_txt_privacyPolicy.setTextColor(getResources().getColor(R.color.white));

                            fab_img_rateUs.show();
                            fab_img_shareApp.show();
                            fab_img_otherApps.show();
                            fab_img_privacyPolicy.show();
                            fab_txt_rateUs.setVisibility(View.VISIBLE);
                            fab_txt_shareApp.setVisibility(View.VISIBLE);
                            fab_txt_otherApps.setVisibility(View.VISIBLE);
                            fab_txt_privacyPolicy.setVisibility(View.VISIBLE);
                            imgViewsettingBg.setVisibility(View.VISIBLE);
                            viewSettingBg.setVisibility(View.VISIBLE);
                            isAllFabsVisible = true;
                        } else {
                            closeFab();
                        }
                    }
                }
                return true;
            }

        });

        // below is the sample action to handle add person
        // FAB. Here it shows simple Toast msg. The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        fab_img_shareApp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(MainActivity.this, "Person Added", Toast.LENGTH_SHORT).show();
                    }
                });

        // below is the sample action to handle add alarm
        // FAB. Here it shows simple Toast msg The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        fab_img_rateUs.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("sddas", "aala");
                        // Toast.makeText(MainActivity.this, "Alarm Added", Toast.LENGTH_SHORT).show();
                    }
                });


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
                    //    Animation anim = new CircularRotateAnimation(homeBoardImage, 500);
//duration of animation
                    //    anim.setDuration(1000);
//start the animation
                     //   homeBoardImage.startAnimation(anim);

                        System.out.println("*******&&&&");
                       // v.animate().cancel();
                        if (sharedPreferences.contains(soundHomeActivity)) {
                            Boolean getSoundFlag = sharedPreferences.getBoolean(soundHomeActivity, false);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            getSoundFlag = !getSoundFlag;
                            editor.putBoolean(soundHomeActivity, getSoundFlag);
                            editor.commit();
                            if (getSoundFlag == true) {
                                rC.cancel();
                                btnImgHomeSound.setImageResource(R.mipmap.sound_on);
                            } else {
                              //  rC.reset();
                                rC = new RotateAnimation(360, 0,
                                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                rC.setDuration(durationMillis);
                                rC.setInterpolator(new LinearInterpolator());
                                rC.setRepeatMode(Animation.RESTART);
                                rC.setRepeatCount(Animation.INFINITE);
                                optionsRelativeLayout.startAnimation(rC);
                                btnImgHomeSound.setImageResource(R.mipmap.sound_off);
                                stopPlayerSound();
                            }
                        }
                    }
                }
                return true;
            }
        });

    }




    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle", "onRestart invoked");
        if (sharedPreferences.contains(soundHomeActivity)) {
            Boolean getSoundFlag = sharedPreferences.getBoolean(soundHomeActivity, false);
            if (getSoundFlag == true) {
                playSound();
            } else {
                stopPlayerSound();
            }
        } else {
            stopPlayerSound();
        }
    }

    public void settingClicked(View v) {
        System.out.println("@@@@@@@@@@@@@@@Home clicked@@@@@@@@@@@");
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void shareAppClicked(View v) {
        fab_txt_shareApp.setTextColor(getResources().getColor(R.color.red_done));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFab();
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, Constant.APP_PNAME);
                    String shareMessage = "Best app for kids to learn clock.\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + Constant.BUNDLE_ID;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        }, 500);

    }

    public void rateAppTabClicked(View v) {
        System.out.println("-------rateAppTabClicked---------------" + getPackageName());
        try {
            fab_txt_rateUs.setTextColor(getResources().getColor(R.color.red_done));
            Uri uri = Uri.parse("market://details?id=" + getPackageName() + "");
            Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goMarket);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName() + "");
            Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goMarket);
        }
    }

    public void privacyTabClicked(View v) {
        fab_txt_privacyPolicy.setTextColor(getResources().getColor(R.color.red_done));
        System.out.println("@@@@@@@@@@@@@@@privacyTabClicked clicked@@@@@@@@@@@");
        Log.d("MyApp", "I am here");

        if (isOnline()) {
            Intent intent = new Intent(this, PrivacyPolicyActivity.class);
            this.startActivity(intent);
        } else {
            closeFab();
            //   Toast.makeText(MainActivity.this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

//    public void guessTimeClicked(View v) {
//        System.out.println("@@@@@@@@@@@@@@@guessTimeClicked clicked@@@@@@@@@@@");
//        guessTimeFinalArray = createRandomTimeArray();
//        Intent intent = new Intent(this, GuessTimeActivity.class);
//        startActivity(intent);
//    }


    public void otherAppsTabClicked(View v) {
        fab_txt_otherApps.setTextColor(getResources().getColor(R.color.red_done));
        //  System.out.println("@@@@@@@@@@@@@@@Our other apps clicked@@@@@@@@@@@");
        Intent intent = new Intent(this, OtherAppsActivity.class);
        startActivity(intent);
    }

    public void closeFab() {
        System.out.println("closeFab");
        if (isAllFabsVisible) {
            System.out.println("isAllFabsVisible");
            fab_img_rateUs.hide();
            fab_img_shareApp.hide();
            fab_img_otherApps.hide();
            fab_img_privacyPolicy.hide();
            fab_txt_rateUs.setVisibility(View.GONE);
            fab_txt_shareApp.setVisibility(View.GONE);
            fab_txt_otherApps.setVisibility(View.GONE);
            fab_txt_privacyPolicy.setVisibility(View.GONE);
            isAllFabsVisible = false;
            imgViewsettingBg.setVisibility(View.INVISIBLE);
            viewSettingBg.setVisibility(View.INVISIBLE);
        }
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
        Log.v("soundHomeBtnClicked", "i m inside homeBtnClicked");
        if (sharedPreferences.contains(soundHomeActivity)) {
            Boolean getSoundFlag = sharedPreferences.getBoolean(soundHomeActivity, false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            getSoundFlag = !getSoundFlag;
            editor.putBoolean(soundHomeActivity, getSoundFlag);
            editor.commit();
            if (getSoundFlag == true) {
                playSound();
                btnImgHomeSound.setImageResource(R.mipmap.sound_on);
            } else {
                btnImgHomeSound.setImageResource(R.mipmap.sound_off);
                stopPlayerSound();
            }
        }
    }

    public void playSound() {
        if (MainActivity.sharedPreferences.getBoolean(soundHomeActivity, false)) {
            if (player != null) {
                player.stop();
                //  player.release();
            }
            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.GKQuiz:raw/" + "bgmusic", null, null);
            player = MediaPlayer.create(getBaseContext(), idSoundBg);
            player.start();
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // System.out.println("@@@@-----------------On completion-----------------@@@@@@");
                if (MainActivity.sharedPreferences.getBoolean(soundHomeActivity, false)) {
                    player.start();
                }
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeFab();
        if (player != null) {
            player.stop();
        }
        // Log.i("eerrr","onStop########");
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