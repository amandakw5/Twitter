package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FollowActivity extends AppCompatActivity {
    FollowAdapter FollowAdapter; // data source
    ArrayList<User> follows;
    JSONArray users;
    RecyclerView rvFollow;
    TwitterClient client;
    User user;
    private EndlessRecyclerViewScrollListener scrollListener;
    public int cursor;
    public int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_follow);
        rvFollow = (RecyclerView) findViewById(R.id.rvFollow);
        client = TwitterApp.getRestClient();
        super.onCreate(savedInstanceState);

        user = (User) Parcels.unwrap(getIntent().getParcelableExtra(User.class.getSimpleName()));
        x = (int) getIntent().getIntExtra("f", x);
        // find the RecyclerView
        // init the arraylist(data sourxe)
        follows = new ArrayList<>();
        users = new JSONArray();
        // construct the adapterb from this datasource
        FollowAdapter = new FollowAdapter(follows);
        rvFollow.setAdapter(FollowAdapter);
        cursor = -1;
        // RecyclerView setup (layout manager, use adapter)
        loadNextDataFromApi(cursor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvFollow.setLayoutManager(new LinearLayoutManager(this));

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(cursor);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvFollow.addOnScrollListener(scrollListener);
        // set the adapter

    }

    public void loadNextDataFromApi(int c) {
        if (x == 1) {
            client.getFollowers(user.uid, -1, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("TwitterClient", response.toString());
                    try {
                        users = response.getJSONArray("users");
                        for (int i = 0; i < users.length(); i++) {
                            // convert each object to a Tweet model
                            // add the Tweet model to our data source
                            // notify the adapter that we've added an item
                            try {
                                User u = User.fromJSON(users.getJSONObject(i));
                                follows.add(u);
                                FollowAdapter.notifyItemInserted(follows.size() - 1);
                                // response.getInt("next_cursor");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
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
        } else {
            client.getFriends(user.uid, -1, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("TwitterClient", response.toString());
                    try {
                        users = response.getJSONArray("users");
                        for (int i = 0; i < users.length(); i++) {
                            // convert each object to a Tweet model
                            // add the Tweet model to our data source
                            // notify the adapter that we've added an item
                            try {
                                User u = User.fromJSON(users.getJSONObject(i));
                                follows.add(u);
                                FollowAdapter.notifyItemInserted(follows.size() - 1);
                                // response.getInt("next_cursor");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
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
}


