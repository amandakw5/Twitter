package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

public class ProfileActivity extends AppCompatActivity {
    Context context;
    ImageView pfProfilePicture;
    TextView pfName;
    TextView pfUserName;
    TextView following;
    TextView Followers;
    User user;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_profile);
        client = TwitterApp.getRestClient();
        user = (User) Parcels.unwrap(getIntent().getParcelableExtra(User.class.getSimpleName()));
        pfProfilePicture = (ImageView) findViewById(R.id.pfProfilePicture);
        pfUserName = (TextView) findViewById(R.id.pfUserName);
        pfName = (TextView) findViewById(R.id.pfName);
        Followers = (TextView) findViewById(R.id.Followers);
        following = (TextView) findViewById(R.id.following);

        Glide.with(context)
                .load(user.profileImageUrl)
                .transform(new CircleTransform(context))
                .into(this.pfProfilePicture);
        pfName.setText(user.name);
        pfUserName.setText("@" + user.screenName);
        Followers.setText(user.followers_count + " Followers");
        following.setText(user.following + " Following");
        Followers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(ProfileActivity.this, FollowActivity.class);
                i.putExtra("f", 1);
                i.putExtra(User.class.getSimpleName(), Parcels.wrap(user));
                startActivity(i);
            }
        });
        following.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(ProfileActivity.this, FollowActivity.class);
                i.putExtra(User.class.getSimpleName(), Parcels.wrap(user));
                i.putExtra("f", 2);
                startActivity(i);
            }
        });

    }

}
