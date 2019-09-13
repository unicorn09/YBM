package com.example.ybm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter ;
    int position = 0 ;
    Button btnGetStarted;
    Animation btnAnim ;
    private TextView[] dots;
    private LinearLayout dotsLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // when this activity is about to be launch we need to check if its openened before or not

        if (restorePrefData()) {

            Intent mainActivity = new Intent(getApplicationContext(),Registration.class );
            startActivity(mainActivity);
            finish();


        }

        setContentView(R.layout.activity_intro);

        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        btnGetStarted.setVisibility(View.INVISIBLE);

        dotsLayout = findViewById(R.id.splash1_layoutDots);

        addBottomDots(0);

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Youth Bihar Movement","is a movement to make Bihar a world class place", "to live", R.drawable.img1));
        mList.add(new ScreenItem("Youth Bihar Movement","is a movement to make Bihar a world class place", "to work",R.drawable.img2));
        mList.add(new ScreenItem("Youth Bihar Movement","is a movement to make Bihar a world class place", "to celebrate life", R.drawable.img3));
        mList.add(new ScreenItem("Youth Bihar Movement", "Let's make this change happen", "", R.drawable.img4));

        // setup viewpager
        screenPager =findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        screenPager.addOnPageChangeListener(viewListener);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getApplicationContext(),Registration.class);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();



            }
        });



    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1)
        {

        }

        @Override
        public void onPageSelected(int i) {
            addBottomDots(i);
            if(i==3)
            {
                btnGetStarted.setAnimation(btnAnim);
                btnGetStarted.setVisibility(View.VISIBLE);
            }
            else
            {
                btnGetStarted.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


    private void addBottomDots(int position)
    {
        dots = new TextView[4];
       // int colorActive = getResources().getColor(R.color.dot_dark_active);
        //int colorInactive = getResources().getColor(R.color.dot_dark_inactive);

        dotsLayout.removeAllViews();
        for(int i=0; i<dots.length; i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
          //  dots[i].setTextColor(colorInactive);
            dotsLayout.addView(dots[i]);
        }
        //if(dots.length>0)
            //dots[position].setTextColor(colorActive);
    }

    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;

    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();


    }

}
