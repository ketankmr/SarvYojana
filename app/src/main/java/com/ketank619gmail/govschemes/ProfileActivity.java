package com.ketank619gmail.govschemes;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    PagerSlidingTabStrip tabsStrip;
    ViewPager viewPager;
    ArrayList<SchemeModel> schemes= new ArrayList<>();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        String schme =getIntent().getExtras().getString("Scheme");

        Type type = new TypeToken<ArrayList<SchemeModel>>() {}.getType();

        schemes =gson.fromJson(schme, type);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        viewPager.setAdapter(new ProfileAdapter(getSupportFragmentManager(),schemes));

        // Give the PagerSlidingTabStrip the ViewPager

        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }
}
