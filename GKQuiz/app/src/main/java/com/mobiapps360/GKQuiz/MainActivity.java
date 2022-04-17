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
import android.widget.Toast;

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
    int xAdd = 100;
    int yAdd = 100;
    ImageButton imgBtnBasicGk;
    ImageButton imgBtnEnglish;
    ImageButton imgBtnWild;
    ImageButton imgBtnSpace;
    ImageButton imgBtnWorldGk;
    ImageButton imgBtnHealth;
    private AdView mAdView;
    AdRequest adRequest;
    public static ArrayList<QuestionItem> questionItemDataArray;
    public static ArrayList<QuestionItem> questionItemFinalArray;

    //    CircleLayoutManager circleLayoutManager;// = new CircleLayoutManager(context);
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
        //  Log.v("xxx", "Inside getSoundFlag loop----");

        //  imgViewHomeGif = findViewById(R.id.imgViewHomeGif);
        btnImgHomeSound = findViewById(R.id.btnSoundHomeScreen);
        homeBoardImage = findViewById(R.id.homeBoardImage);
        btnImgSetting = findViewById(R.id.btnSetting);
        imgViewWallBg = findViewById(R.id.mainWallImage);
        viewSettingBg = findViewById(R.id.viewSettingBg);
        imgViewsettingBg = findViewById(R.id.imgViewSettingBg);
        optionsRelativeLayout = (RelativeLayout) findViewById(R.id.cont);


        System.out.println("Inside --" + Resources.getSystem().getDisplayMetrics().widthPixels);
        Display display = getWindowManager().getDefaultDisplay();
        Point m_size = new Point();
        display.getSize(m_size);
        int m_width = m_size.x;
        int m_height = m_size.y;
        getwidth = (int) (1080 / Resources.getSystem().getDisplayMetrics().density);

        xAdd = getwidth;
        yAdd = getwidth;


//        System.out.println("My Width : "+ m_width);
//        System.out.println("My Height : "+ (int) (1080 / Resources.getSystem().getDisplayMetrics().density));



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
                 Toast.makeText(MainActivity.this,"ad loaded",Toast.LENGTH_SHORT).show();
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


        imgBtnBasicGk = findViewById(R.id.imgBtnBasicGk);
        imgBtnEnglish = findViewById(R.id.imgBtnEnglish);
        imgBtnWild = findViewById(R.id.imgBtnWild);
        imgBtnSpace = findViewById(R.id.imgBtnSpace);
        imgBtnWorldGk = findViewById(R.id.imgBtnWorldGk);
        imgBtnHealth = findViewById(R.id.imgBtnHealth);

        imgBtnBasicGk.setBackgroundResource(R.mipmap.basic_gk);
        imgBtnEnglish.setBackgroundResource(R.mipmap.english);
        imgBtnWild.setBackgroundResource(R.mipmap.wild_aquatic);
        imgBtnSpace.setBackgroundResource(R.mipmap.space);
        imgBtnWorldGk.setBackgroundResource(R.mipmap.world_gk);
        imgBtnHealth.setBackgroundResource(R.mipmap.health);


        RelativeLayout.LayoutParams layoutParam1 = new RelativeLayout.LayoutParams(250, 250);
        float angleDeg = 0 * 360.0f / 6 - 90.0f;
        float angleRad = (float) (angleDeg * Math.PI / 180.0f);
        layoutParam1.leftMargin = (int) (getwidth * (float) Math.cos(angleRad)) + xAdd;
        layoutParam1.topMargin = (int) (getwidth * (float) Math.sin(angleRad)) + yAdd;
        imgBtnBasicGk.setLayoutParams(layoutParam1);


        RelativeLayout.LayoutParams layoutParam2 = new RelativeLayout.LayoutParams(250, 250);
        angleDeg = 5 * 360.0f / 6 - 90.0f;
        angleRad = (float) (angleDeg * Math.PI / 180.0f);
        layoutParam2.leftMargin = (int) (getwidth * (float) Math.cos(angleRad)) + xAdd;
        layoutParam2.topMargin = (int) (getwidth * (float) Math.sin(angleRad)) + yAdd;
        imgBtnEnglish.setLayoutParams(layoutParam2);

        RelativeLayout.LayoutParams layoutParam3 = new RelativeLayout.LayoutParams(250, 250);
        angleDeg = 2 * 360.0f / 6 - 90.0f;
        angleRad = (float) (angleDeg * Math.PI / 180.0f);
        layoutParam3.leftMargin = (int) (getwidth * (float) Math.cos(angleRad)) + xAdd;
        layoutParam3.topMargin = (int) (getwidth * (float) Math.sin(angleRad)) + yAdd;
        imgBtnWild.setLayoutParams(layoutParam3);

        RelativeLayout.LayoutParams layoutParam4 = new RelativeLayout.LayoutParams(250, 250);
        angleDeg = 3 * 360.0f / 6 - 90.0f;
        angleRad = (float) (angleDeg * Math.PI / 180.0f);
        layoutParam4.leftMargin = (int) (getwidth * (float) Math.cos(angleRad)) + xAdd;
        layoutParam4.topMargin = (int) (getwidth * (float) Math.sin(angleRad)) + yAdd;
        imgBtnSpace.setLayoutParams(layoutParam4);

        RelativeLayout.LayoutParams layoutParam5 = new RelativeLayout.LayoutParams(250, 250);
        angleDeg = 4 * 360.0f / 6 - 90.0f;
        angleRad = (float) (angleDeg * Math.PI / 180.0f);
        layoutParam5.leftMargin = (int) (getwidth * (float) Math.cos(angleRad)) + xAdd;
        layoutParam5.topMargin = (int) (getwidth * (float) Math.sin(angleRad)) + yAdd;
        imgBtnWorldGk.setLayoutParams(layoutParam5);

        RelativeLayout.LayoutParams layoutParam6 = new RelativeLayout.LayoutParams(250, 250);
        angleDeg = 1 * 360.0f / 6 - 90.0f;
        angleRad = (float) (angleDeg * Math.PI / 180.0f);
        layoutParam6.leftMargin = (int) (getwidth * (float) Math.cos(angleRad)) + xAdd;
        layoutParam6.topMargin = (int) (getwidth * (float) Math.sin(angleRad)) + yAdd;
        imgBtnHealth.setLayoutParams(layoutParam6);

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

        //Parse json files to array


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
                        System.out.println("*******&&&&");
                        // v.animate().cancel();
                        if (sharedPreferences.contains(soundHomeActivity)) {
                            Boolean getSoundFlag = sharedPreferences.getBoolean(soundHomeActivity, false);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            getSoundFlag = !getSoundFlag;
                            editor.putBoolean(soundHomeActivity, getSoundFlag);
                            editor.commit();
                            if (getSoundFlag == true) {
                                btnImgHomeSound.setImageResource(R.mipmap.sound_on);
                                playSound();
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

        imgBtnBasicGk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Basic Gk clicked.");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        playSoundOptionClick();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        resetOptions();
                        v.setBackgroundResource(R.mipmap.basic_gk_selected);
                        imgBtnBasicGk.setAlpha(1.0f);
                        basicGkClicked();
                    }
                }
                return true;
            }
        });

        imgBtnEnglish.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Basic Gk clicked.");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        playSoundOptionClick();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        resetOptions();
                        v.setBackgroundResource(R.mipmap.english_selected);
                        imgBtnEnglish.setAlpha(1.0f);
                        englishGrammarClicked();
                    }
                }
                return true;
            }
        });


        imgBtnHealth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Basic Gk clicked.");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        playSoundOptionClick();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        resetOptions();
                        v.setBackgroundResource(R.mipmap.health_selected);
                        imgBtnHealth.setAlpha(1.0f);
                        humanBodyClicked();
                    }
                }
                return true;
            }
        });

        imgBtnWild.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Basic Gk clicked.");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        playSoundOptionClick();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        resetOptions();
                        v.setBackgroundResource(R.mipmap.wild_aquatic_selected);
                        imgBtnWild.setAlpha(1.0f);
                        wildAquaticClicked();
                    }
                }
                return true;
            }
        });

        imgBtnSpace.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Basic Gk clicked.");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setAlpha((float) 0.5);
                        playSoundOptionClick();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        resetOptions();
                        v.setBackgroundResource(R.mipmap.space_selected);
                        imgBtnSpace.setAlpha(1.0f);
                        spaceClicked();
                    }
                }
                return true;
            }
        });

        imgBtnWorldGk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Basic Gk clicked.");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        playSoundOptionClick();
                        ((ImageButton) v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setAlpha((float) 1.0);
                        resetOptions();
                        v.setBackgroundResource(R.mipmap.world_gk_selected);
                        imgBtnWorldGk.setAlpha(1.0f);
                        worldGeographyClicked();
                    }
                }
                return true;
            }
        });
    }

    public ArrayList<QuestionItem> createRandomTimeArray() {
        System.out.println("Inside createarray---+" + questionItemDataArray);
        int maxCount = questionItemDataArray.size();
        questionItemFinalArray = new ArrayList<QuestionItem>();
        ArrayList<Integer> selectedArrayOfIndex = new ArrayList<Integer>();
        for (int i = 0; i < maxCount; i++) {

            Boolean isQuestionSelected = false;
            do {
                Random rand = new Random();
                int random = rand.nextInt(maxCount);
                if (!(selectedArrayOfIndex.contains(random))) {
                    selectedArrayOfIndex.add(random);
                    questionItemFinalArray.add(questionItemDataArray.get(random));
                    isQuestionSelected = true;
                }

            } while (!isQuestionSelected);
        }
        return questionItemFinalArray;
    }

    public ArrayList parseQuestionArray(String fileName) {

        String strJson = null;
        ArrayList<QuestionItem> questionItemDataArray;
        questionItemDataArray = new ArrayList<>();

        try {
            strJson = readFile(fileName + ".json");
            //  System.out.println("parseGuessTimeArray strJson****"+strJson);

            String data = "";
            try {

                // Create the root JSONObject from the JSON string.
                JSONObject jsonRootObject = new JSONObject(strJson);
                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonRootObject.optJSONArray(fileName);
                System.out.println("jsonArray#####" + jsonArray);

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    try {
                        //  System.out.println("jsonObject#####"+jsonObject);
                        JSONArray jOptions = jsonObject.getJSONArray("option");
                        ArrayList<QuestionOptionModel> listOptions = new ArrayList<QuestionOptionModel>();

                        for (int iCnt = 0; iCnt < jOptions.length(); iCnt++) {
                            if (jOptions.length() != 0) {
                                JSONObject jsonOptionObject = jOptions.getJSONObject(iCnt);
                                QuestionOptionModel questionOptionModel = new QuestionOptionModel(jsonOptionObject.getString("optionStr"), jsonOptionObject.getInt("optionStatus"));
                                listOptions.add(questionOptionModel);
                            }
                        }
                        QuestionItem questionItem = new QuestionItem(jsonObject.getString("question"), listOptions, jsonObject.getInt("answer"));
                        //  System.out.println("guessTimeItem!!!!!"+guessTimeItem);
                        //  System.out.println("yyyyyyy"+quizItem.arrayCaption);
                        questionItemDataArray.add(questionItem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("----guessTimeDataArray---" + questionItemDataArray);
                return questionItemDataArray;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionItemDataArray;
    }

    public String readFile(String fileName) throws IOException {
        System.out.println("----inside readFile---");
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName), "UTF-8"));

        String content = "";
        String line;
        while ((line = reader.readLine()) != null) {
            content = content + line;
        }
        return content;
    }

    void resetOptions() {
        imgBtnWorldGk.setBackgroundResource(R.mipmap.world_gk);
        imgBtnEnglish.setBackgroundResource(R.mipmap.english);
        imgBtnHealth.setBackgroundResource(R.mipmap.health);
        imgBtnSpace.setBackgroundResource(R.mipmap.space);
        imgBtnWild.setBackgroundResource(R.mipmap.wild_aquatic);
        imgBtnBasicGk.setBackgroundResource(R.mipmap.basic_gk);

        imgBtnBasicGk.setAlpha(0.8f);
        imgBtnEnglish.setAlpha(0.8f);
        imgBtnHealth.setAlpha(0.8f);
        imgBtnSpace.setAlpha(0.8f);
        imgBtnWild.setAlpha(0.8f);
        imgBtnWorldGk.setAlpha(0.8f);
    }

    protected void onRestart() {
        super.onRestart();
        imgBtnBasicGk.setAlpha(1.0f);
        imgBtnEnglish.setAlpha(1.0f);
        imgBtnHealth.setAlpha(1.0f);
        imgBtnSpace.setAlpha(1.0f);
        imgBtnWild.setAlpha(1.0f);
        imgBtnWorldGk.setAlpha(1.0f);
        imgBtnWorldGk.setBackgroundResource(R.mipmap.world_gk);
        imgBtnEnglish.setBackgroundResource(R.mipmap.english);
        imgBtnHealth.setBackgroundResource(R.mipmap.health);
        imgBtnSpace.setBackgroundResource(R.mipmap.space);
        imgBtnWild.setBackgroundResource(R.mipmap.wild_aquatic);
        imgBtnBasicGk.setBackgroundResource(R.mipmap.basic_gk);
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
                    String shareMessage = "Best app for kids to learn GK.\n\n";
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

    public void basicGkClicked() {
        questionItemDataArray = parseQuestionArray("basic_gk");
        questionItemFinalArray = createRandomTimeArray();
        Intent intent = new Intent(this, QuizActivity.class);
        Constant.arrayXyz = questionItemFinalArray;
        intent.putExtra("screenTitle", "Basic General Knowledge");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFab();
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    //e.toString();
                }
            }
        }, 500);
    }

    public void humanBodyClicked() {
        questionItemDataArray = parseQuestionArray("human_body");
        questionItemFinalArray = createRandomTimeArray();
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("screenTitle", "Health & Human Body");
        Constant.arrayXyz = questionItemFinalArray;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFab();
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    //e.toString();
                }
            }
        }, 500);
    }

    public void spaceClicked() {
        questionItemDataArray = parseQuestionArray("space");
        questionItemFinalArray = createRandomTimeArray();
        Intent intent = new Intent(this, QuizActivity.class);
        Constant.arrayXyz = questionItemFinalArray;
        intent.putExtra("screenTitle", "Space");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFab();
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    //e.toString();
                }
            }
        }, 500);
    }

    public void wildAquaticClicked() {
        questionItemDataArray = parseQuestionArray("wild_aquatic");
        questionItemFinalArray = createRandomTimeArray();
        Intent intent = new Intent(this, QuizActivity.class);
        Constant.arrayXyz = questionItemFinalArray;
        intent.putExtra("screenTitle", "Wild & Aquatic Life");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFab();
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    //e.toString();
                }
            }
        }, 500);
    }

    public void worldGeographyClicked() {
        questionItemDataArray = parseQuestionArray("world_geography");
        questionItemFinalArray = createRandomTimeArray();
        Intent intent = new Intent(this, QuizActivity.class);
        Constant.arrayXyz = questionItemFinalArray;
        intent.putExtra("screenTitle", "World's Geography");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFab();
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    //e.toString();
                }
            }
        }, 500);
    }

    public void englishGrammarClicked() {
        questionItemDataArray = parseQuestionArray("eglish_grammar");
        questionItemFinalArray = createRandomTimeArray();
        Intent intent = new Intent(this, QuizActivity.class);
        Constant.arrayXyz = questionItemFinalArray;
        intent.putExtra("screenTitle", "English Grammar");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFab();
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    //e.toString();
                }
            }
        }, 500);
    }

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

    void playSoundOptionClick() {
        if (MainActivity.sharedPreferences.getBoolean(soundHomeActivity, false)) {
            if (player != null) {
                player.stop();
                //  player.release();
            }


            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.GKQuiz:raw/option_click_sound", null, null);
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
    public void onResume() {
        super.onResume();


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