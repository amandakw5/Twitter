package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.codepath.apps.restclienttemplate.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimelineActivity;
import com.codepath.apps.restclienttemplate.Tweet;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

import static com.codepath.apps.restclienttemplate.R.id.swipeContainer;

/**
 * Created by awestort on 7/3/17.
 */


public class TweetsListFragment extends Fragment  implements TweetAdapter.TweetAdapterListener{
    public interface TweetSelectedListener {
        // handle tweet selection
        public void onTweetSelected(Tweet tweet);
    }
    public TweetAdapter tweetAdapter; // data source
    public List<Tweet> tweets;
    RecyclerView rvTweets;

    private final int REQUEST_CODE = 20;
    Tweet newtweet;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar miActionProgress;
    ImageView reply;
    private EndlessRecyclerViewScrollListener scrollListener;
    private final Handler handler = new Handler();
    private Timer autoUpdate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);
        setHasOptionsMenu(true);
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        reply = (ImageView) v.findViewById(R.id.dtreply);
        miActionProgress = (ProgressBar) v.findViewById(R.id.miActionProgress);
        //client = TwitterApp.getRestClient();
        // init the arraylist(data sourxe)
        tweets = new ArrayList<>();
        if (!TimelineActivity.internet) {
            tweets.addAll(SQLite.select().
                    from(Tweet.class).queryList());
            Collections.reverse(tweets);
        }
        deleteData(tweets);
//        // construct the adapterb from this datasource
        tweetAdapter = new TweetAdapter(tweets, this);
//        // RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(linearLayoutManager);
//        // set the adapter
        rvTweets.setAdapter(tweetAdapter);
//        populateTimeline();
        hideProgressBar();
        rvTweets.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(swipeContainer);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgressBar();
                tweetAdapter.clear();
                populateTimeline();
                refreshLayout.setRefreshing(false);
                hideProgressBar();
            }
        });
        // doTheAutoRefresh();
        return v;
    }

    public void populateTimeline() {
    }
    public void showProgressBar() {
        // Show progress item
        miActionProgress.setVisibility(View.VISIBLE );
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgress.setVisibility( View.GONE );
    }
    public void loadNextDataFromApi(int page) {
    }


    public void addItems(JSONArray response){
        try {
            for(int i = 0; i < response.length(); i++){
                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweet.save();
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size() -1);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

    }
    public void deleteData(List<Tweet> t){
        for (int i = 0; i <t.size(); i++ ){
            Tweet tweet = t.get(i);
            tweet.delete();
        }
    }


    @Override
    public void onItemsSelected(View view, int position){
        Tweet tweet = tweets.get(position);
        //Toast.makeText(getContext(), tweet.body, Toast.LENGTH_SHORT).show();
        ((TweetSelectedListener) getActivity()).onTweetSelected(tweet);
    }
    @Override
    public void onResume() {
        super.onResume();
        populateTimeline();
    }
    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                populateTimeline();
            }
        }, 1000);
    }

}
