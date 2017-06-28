package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.restclienttemplate.R.id.swipeContainer;

public class TimelineActivity extends AppCompatActivity {

    TwitterClient client;
    TweetAdapter tweetAdapter; // data source
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    private final int REQUEST_CODE = 20;
    Tweet newtweet;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApp.getRestClient();

        // find the RecyclerView
        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
        // init the arraylist(data sourxe)
        tweets = new ArrayList<>();
        // construct the adapterb from this datasource
        tweetAdapter = new TweetAdapter(tweets);
        // RecyclerView setup (layout manager, use adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        // set the adapter
        rvTweets.setAdapter(tweetAdapter);
        populateTimeline();
        refreshLayout = (SwipeRefreshLayout) findViewById(swipeContainer);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                tweetAdapter.clear();
                //fetchTimelineAsync(0);
                populateTimeline();
                refreshLayout.setRefreshing(false);
            }
        });

        // Configure the refreshing colors
    }
//    public void fetchTimelineAsync(int page) {
//        // Send the network request to fetch the updated data
//        // `client` here is an instance of Android Async HTTP
//        // getHomeTimeline is an example endpoint.
//
//        client.getHomeTimeline(0, new JsonHttpResponseHandler() {
//            public void onSuccess(JSONArray json) {
//                // Remember to CLEAR OUT old items before appending in the new ones
//
//                // ...the data has come back, add new items to your adapter...
//                TweetAdapter.addAll(json);
//                // Now we call setRefreshing(false) to signal refresh has finished
//
//            }
//
//            public void onFailure(Throwable e) {
//                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
//            }
//        });
//    }




    //initSwipeOptions();
        // Setup refresh listener which triggers new data loading

//    private void initSwipeOptions() {
//        refreshLayout.setOnRefreshListener(this);
//        setAppearance();
//        disableSwipe();
//    }
//
//    private void setAppearance() {
//        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//    }
//
//    /**
//     * It shows the SwipeRefreshLayout progress
//     */
//    public void showSwipeProgress() {
//        refreshLayout.setRefreshing(true);
//    }
//
//    /**
//     * It shows the SwipeRefreshLayout progress
//     */
//    public void hideSwipeProgress() {
//        refreshLayout.setRefreshing(false);
//    }
//
//    /**
//     * Enables swipe gesture
//     */
//    public void enableSwipe() {
//        refreshLayout.setEnabled(true);
//    }
//
//    /**
//     * Disables swipe gesture. It prevents manual gestures but keeps the option tu show
//     * refreshing programatically.
//     */
//    public void disableSwipe() {
//        refreshLayout.setEnabled(false);
//    }
//
//    /**
//     * It must be overriden by parent classes if manual swipe is enabled.
//     */
//    @Override public void onRefresh() {
//        // Empty implementation
//    }
//    refreshLayout.setOnScrollListener(new AbsListView.OnScrollListener() {
//        @Override
//        public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//        }
//
//        @Override
//        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            int topRowVerticalPosition =
//                    (guidesList == null || guidesList.getChildCount() == 0) ?
//                            0 : guidesList.getChildAt(0).getTop();
//            swipeContainer.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
//        }
//    });
//

//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false)
//                // once the network request has completed successfully.
//                fetchTimelineAsync(0);
//            }
//        });
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//    }
//    public void fetchTimelineAsync(int page) {
//        // Send the network request to fetch the updated data
//        // `client` here is an instance of Android Async HTTP
//        // getHomeTimeline is an example endpoint.
//
//        client.getHomeTimeline(0, new JsonHttpResponseHandler() {
//            public void onSuccess(JSONArray json) {
//                // Remember to CLEAR OUT old items before appending in the new ones
//                tweetAdapter.clear();
//                // ...the data has come back, add new items to your adapter...
//                tweetAdapter.addAll(tweets);
//                // Now we call setRefreshing(false) to signal refresh has finished
//                swipeContainer.setRefreshing(false);
//            }
//
//            public void onFailure(Throwable e) {
//                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
//            }
//        });
//    }


    private void populateTimeline() {
        client.getHomeTimeline(0, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
               // Log.d("TwitterClient", response.toString());
                // iterate through the JSON array
                // for each entry, deserialize the JSON object
                for (int i = 0; i < response.length(); i++){
                    // convert each object to a Tweet model
                    // add the Tweet model to our data source
                    // notify the adpter that we've added an item
                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.theicon:
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                i.putExtra("mode", 2); // pass arbitrary data to launched activity
                startActivityForResult(i, REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            newtweet = Parcels.unwrap(data.getParcelableExtra("twee"));
            Toast.makeText(this, "tweeted", Toast.LENGTH_SHORT).show();
            tweets.add(0, newtweet);
            tweetAdapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);
        }
    }
}
