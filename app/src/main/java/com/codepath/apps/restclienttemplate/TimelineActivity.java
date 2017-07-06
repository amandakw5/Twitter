package com.codepath.apps.restclienttemplate;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.ComposeFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener {
    private final int REQUEST_CODE = 20;

    MenuItem miActionProgressItem;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        FlowManager.init(new FlowConfig.Builder(this).build());
        // get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
        getSupportActionBar().setTitle("Home");
        //setup the Tablayout to use the view pager


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        tabLayout.getTabAt(0).setIcon(tabIconsSelected[0]);
        for (int i = 1; i < tabIconsUnselected.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIconsUnselected[i]);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(tabIconsSelected[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(tabIconsUnselected[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void showEditDialog() {
        FragmentManager fm = getFragmentManager();
        ComposeFragment editNameDialogFragment = ComposeFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "activity_compose");
    }

    public void onProfileView(MenuItem item){
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screenName", "");
        startActivity(i);
    }
    private int[] tabIconsUnselected = {
            R.drawable.ic_vector_home_stroke,
            R.drawable.ic_vector_search_stroke,
            R.drawable.ic_vector_notifications_stroke,
            R.drawable.message
    };
    private int[] tabIconsSelected = {
            R.drawable.ic_vector_home,
            R.drawable.ic_vector_search,
            R.drawable.ic_vector_notifications,
            R.drawable.ic_vector_messages
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.theicon:
                 showEditDialog();
//                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
//                i.putExtra("mode", 2); // pass arbitrary data to launched activity
//                startActivityForResult(i, REQUEST_CODE);
                return true;
            case R.id.menuProfile:
                Intent in = new Intent(TimelineActivity.this, ProfileActivity.class);
                in.putExtra("screeName", ""); // pass arbitrary data to launched activity
                startActivityForResult(in, REQUEST_CODE);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTweetSelected(Tweet tweet) {
        Intent i = new Intent(this, TweetDetails.class);
        startActivity(i);
    }
    public void onFinishEditDialog(String t) {
        client = TwitterApp.getRestClient();
        client.sendTweet(t, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
                try {
                    Tweet t = Tweet.fromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });

    }
}
