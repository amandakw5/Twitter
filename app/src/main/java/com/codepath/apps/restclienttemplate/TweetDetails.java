package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TweetDetails extends AppCompatActivity {
    @BindView(R.id.dtreply) ImageView reply;
    Tweet tweet;
    Context context;
    @BindView(R.id.dtProfilePicture) ImageView dtProfilePicture;
    @BindView(R.id.dtName) TextView dtName;
    @BindView(R.id.dtUserName) TextView dtUserName;
    @BindView(R.id.dtBody) TextView dtBody;
    @BindView(R.id.dtHeart) ImageView dtHeart;
    TwitterClient client;
    String imageUrl;
    @BindView(R.id.dtRetweet) ImageView dtRetweet;
    @BindView(R.id.dtmedia) ImageView dtmedia;
    String mediaUrl;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = TwitterApp.getRestClient();
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        ButterKnife.bind(this);
        mediaUrl = tweet.mediaUrl;
        imageUrl = tweet.user.profileImageUrl;
        Glide.with(context)
                .load(imageUrl)
                .transform(new CircleTransform(context))
                .into(this.dtProfilePicture);
        if (mediaUrl != null){
            Glide.with(context)
                    .load(mediaUrl)
                    .into(this.dtmedia);
        }
        else{
            dtmedia.setVisibility(View.GONE);
        }


        dtName.setText(tweet.user.name);
        dtUserName.setText("@" + tweet.user.screenName);
        dtBody.setText(tweet.body);

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TweetDetails.this, ReplyTweet.class);
                //i.putExtra(Tweet.);
                i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                startActivity(i);
            }
        });
        dtHeart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (tweet.favorited) {
                    Glide.with(context).load(R.drawable.heart).into(dtHeart);
                    client.unfavoriteTweet(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("TwitterClient", response.toString());
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
                    tweet.favorited = false;
                } else {
                    Glide.with(context).load(R.drawable.hearted).into(dtHeart);
                    client.favoriteTweet(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("TwitterClient", response.toString());
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
                    tweet.favorited = true;
                }
            }
        });



        dtRetweet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                client.retweetTweet(tweet.uid, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("TwitterClient", response.toString());
                        Toast.makeText(TweetDetails.this, "Retweeted", Toast.LENGTH_SHORT).show();
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
        });
    }

}
