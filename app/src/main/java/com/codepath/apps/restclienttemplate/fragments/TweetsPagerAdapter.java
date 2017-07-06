package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by awestort on 7/3/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] { "", "", "", "" };
    private Context context;

    public TweetsPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeTimelineFragment();
            default:
                return new MentionsTimelineFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }
}
