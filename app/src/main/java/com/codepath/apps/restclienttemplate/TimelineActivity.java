package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;

public class TimelineActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 20;
//    TweetAdapter tweetAdapter; // data source
//    ArrayList<Tweet> tweets;
//    RecyclerView rvTweets;
//    TwitterClient client;
    TweetsListFragment fragmentTweetsList;
//
//    private final int REQUEST_CODE = 20;
//    Tweet newtweet;
//    private SwipeRefreshLayout refreshLayout;
    MenuItem miActionProgressItem;
//    ImageView reply;
//    private EndlessRecyclerViewScrollListener scrollListener;
//    private final Handler handler = new Handler();
//    private Timer autoUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

//        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
//        reply = (ImageView) findViewById(R.id.dtreply);
        //client = TwitterApp.getRestClient();
        fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);

        // init the arraylist(data sourxe)
//        tweets = new ArrayList<>();
//        // construct the adapterb from this datasource
//        tweetAdapter = new TweetAdapter(tweets);
//        // RecyclerView setup (layout manager, use adapter)
//        rvTweets.setLayoutManager(new LinearLayoutManager(this));
//        // set the adapter
//        rvTweets.setAdapter(tweetAdapter);
        //  populateTimeline();
        //  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rvTweets.setLayoutManager(linearLayoutManager);
//        // Retain an instance so that you can call `resetState()` for fresh searches
//        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to the bottom of the list
//               loadNextDataFromApi(page);
//            }
//        };
//        // Adds the scroll listener to RecyclerView
////        rvTweets.addOnScrollListener(scrollListener);
////        refreshLayout = (SwipeRefreshLayout) findViewById(swipeContainer);
////        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
////            @Override
////            public void onRefresh() {
////                showProgressBar();
////                tweetAdapter.clear();
////                populateTimeline();
////                refreshLayout.setRefreshing(false);
////                hideProgressBar();
////            }
////        });
//       // doTheAutoRefresh();
//    }
//    // Append the next page of data into the adapter
//    // This method probably sends out a network request and appends new data items to your adapter.
////    public void loadNextDataFromApi(int offset) {
////        client.getHomeTimeline(0, offset * 25, new JsonHttpResponseHandler() {
////                    @Override
////                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
////                        Log.d("TwitterClient", response.toString());
////                    }
////                    @Override
////                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
////                        // Log.d("TwitterClient", response.toString());
////                        // iterate through the JSON array
////                        // for each entry, deserialize the JSON object
////                        for (int i = 0; i < response.length(); i++){
////                            // convert each object to a Tweet model
////                            // add the Tweet model to our data source
////                            // notify the adpter that we've added an item
////                            try {
////                                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
////                                tweets.add(tweet);
////                                tweetAdapter.notifyItemInserted(tweets.size() - 1);
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
////                        Log.d("TwitterClient", responseString);
////                        throwable.printStackTrace();
////                    }
////
////                    @Override
////                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
////                        Log.d("TwitterClient", errorResponse.toString());
////                        throwable.printStackTrace();
////                    }
////
////                    @Override
////                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
////                        Log.d("TwitterClient", errorResponse.toString());
////                        throwable.printStackTrace();
////                    }
////                });
////    }
////
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // Store instance of the menu item containing progress
//        miActionProgressItem = menu.findItem(R.id.miActionProgress);
//         //Extract the action-view from the menu item
//        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
//        // Return to finish
//        return super.onPrepareOptionsMenu(menu);
//    }
//    public void showProgressBar() {
//        // Show progress item
//        miActionProgressItem.setVisible(true);
//    }
//
//    public void hideProgressBar() {
//        // Hide progress item
//        miActionProgressItem.setVisible(false);
//    }
////
//////    private void populateTimeline() {
//////        client.getHomeTimeline(0, 1, new JsonHttpResponseHandler(){
//////            @Override
//////            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//////                Log.d("TwitterClient", response.toString());
//////            }
//////
//////            @Override
//////            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//////              fragmentTweetsList.addItems(response);
//////               // Log.d("TwitterClient", response.toString());
//////                // iterate through the JSON array
//////                // for each entry, deserialize the JSON object
////////                for (int i = 0; i < response.length(); i++){
////////                    // convert each object to a Tweet model
////////                    // add the Tweet model to our data source
////////                    // notify the adpter that we've added an item
////////                    try {
////////                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
////////                        tweets.add(tweet);
////////                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
////////                    } catch (JSONException e) {
////////                        e.printStackTrace();
////////                    }
//////                //}
//////            }
//////
//////            @Override
//////            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//////                Log.d("TwitterClient", responseString);
//////                throwable.printStackTrace();
//////            }
//////
//////            @Override
//////            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//////                Log.d("TwitterClient", errorResponse.toString());
//////                throwable.printStackTrace();
//////            }
//////
//////            @Override
//////            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//////                Log.d("TwitterClient", errorResponse.toString());
//////                throwable.printStackTrace();
//////            }
//////        });
//////    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle presses on the action bar items
//        switch (item.getItemId()) {
//            case R.id.theicon:
//                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
//                i.putExtra("mode", 2); // pass arbitrary data to launched activity
//                startActivityForResult(i, REQUEST_CODE);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
        //  @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // REQUEST_CODE is defined above
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            // Extract name value from result extras
//            newtweet = Parcels.unwrap(data.getParcelableExtra("twee"));
//            Toast.makeText(this, "tweeted", Toast.LENGTH_SHORT).show();
//            tweets.add(0, newtweet);
//            tweetAdapter.notifyItemInserted(0);
//            rvTweets.scrollToPosition(0);
//        }
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        populateTimeline();
//    }
//    private void doTheAutoRefresh() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                populateTimeline();
//            }
//        }, 1000);
//    }
    }
}
