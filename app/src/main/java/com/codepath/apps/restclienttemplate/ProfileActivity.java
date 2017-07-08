package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    Context context;
    @BindView (R.id.pfProfilePicture) ImageView pfProfilePicture;
    @BindView(R.id.pfName) TextView pfName;
    @BindView(R.id.pfUserName) TextView pfUserName;
    @BindView(R.id.following) TextView following;
    @BindView(R.id.Followers) TextView Followers;
    @BindView(R.id.pfBio) TextView pfBio;
    User user;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String screenName = getIntent().getStringExtra("screenName");
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName, 1);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //make change
        ft.replace(R.id.flContainer, userTimelineFragment);
        //commit
        ft.commit();
        context = this;
        setContentView(R.layout.activity_profile);
        client = TwitterApp.getRestClient();
        ButterKnife.bind(this);
        if (screenName.equals("")) {
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // deserialize the User object
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        else {
            client.getUserShow(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // deserialize the User object
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    public void populateUserHeadline(User user){
        final User u = user;

        Glide.with(context)
                .load(user.profileImageUrl)
                .transform(new CircleTransform(context))
                .into(this.pfProfilePicture);
        pfName.setText(user.name);
        pfUserName.setText("@" + user.screenName);
        Followers.setText(user.followers_count + " Followers");
        following.setText(user.following + " Following");
        pfBio.setText(user.bio);
        Followers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(ProfileActivity.this, FollowActivity.class);
                i.putExtra("f", 1);
                i.putExtra(User.class.getSimpleName(), Parcels.wrap(u));
                startActivity(i);
            }
        });
        following.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(ProfileActivity.this, FollowActivity.class);
                i.putExtra(User.class.getSimpleName(), Parcels.wrap(u));
                i.putExtra("f", 2);
                startActivity(i);
            }
        });
    }
}
