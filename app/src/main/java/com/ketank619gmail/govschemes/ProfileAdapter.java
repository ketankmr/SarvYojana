package com.ketank619gmail.govschemes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import java.util.ArrayList;

/**
 * Created by Ketan-PC on 12/18/2017.
 */

public class ProfileAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Personalised Schemes", "Queries"};
    ArrayList<SchemeModel> schemes;


    public ProfileAdapter(FragmentManager fm, ArrayList schemes) {
        super(fm);
        this.schemes = schemes;

    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0) {
            return new MySchemeFragment(schemes);
        }
        else {
            return new AnsweredQueries();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}

