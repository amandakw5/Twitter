package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;

import org.parceler.Parcels;

public class TweetDetails extends AppCompatActivity {
    ImageView reply;
    Tweet tweet;
    Context context;
    ImageView dtProfilePicture;
    TextView dtName;
    TextView dtUserName;
    TextView dtBody;
    ImageView favorite;
    AsyncHttpClient client;
    String imageUrl;
    User user;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        dtProfilePicture = (ImageView) findViewById(R.id.dtProfilePicture);
        imageUrl = tweet.user.profileImageUrl;
        Glide.with(context)
                .load(imageUrl)
                .into(this.dtProfilePicture);
        reply = (ImageView) findViewById(R.id.dtreply);
        dtUserName = (TextView) findViewById(R.id.dtUserName);
        dtName = (TextView) findViewById(R.id.dtName);
        dtBody = (TextView) findViewById(R.id.dtBody);
        dtName.setText(tweet.user.name);
        dtUserName.setText("@" + tweet.user.screenName);
        dtBody.setText(tweet.body);

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TweetDetails.this, ReplyTweet.class);
                i.putExtra("username", tweet.user.screenName);
                startActivity(i);
            }
        });
    }
}
