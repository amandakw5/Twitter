package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by awestort on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    List<Tweet> mTweets;
    Context context;
    private final int REQUEST_CODE = 20;
    Tweet newtweet;

    // pass in the tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }
    // for each row, inflate the layout and cache references into ViewHolder
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data according to position
        Tweet tweet = mTweets.get(position);

        // populate the views according to this data
        holder.tvUserName.setText("@" + tweet.user.screenName);
        holder.tvBody.setText(tweet.body);
        holder.tvName.setText(tweet.user.name);
        holder.tvCreated.setText(getRelativeTimeAgo(tweet.createdAt));

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);
    }
    @Override
    public int getItemCount(){
        return mTweets.size();
    }

    // create ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvBody;
        public TextView tvName;
        public TextView tvCreated;
        public ImageView dtreply;

        public ViewHolder(View itemView){
            super(itemView);
            // perform findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            dtreply = (ImageView) itemView.findViewById(R.id.dtreply);
            tvCreated = (TextView) itemView.findViewById(R.id.tvCreated);
            dtreply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Tweet tweet = mTweets.get(position);
                    Intent i = new Intent(context, ReplyTweet.class);
                    i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                    ((TimelineActivity) context).startActivity(i);
                }
            });
        }
        protected void onActivityResult(int requestCode,int resultCode, Intent data) {
            // REQUEST_CODE is defined above
            if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
                // Extract name value from result extras
                newtweet = Parcels.unwrap(data.getParcelableExtra("twee"));
                mTweets.add(0, newtweet);
                notifyItemInserted(0);
            }
        }
        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the tweet at the position, this won't work if the class is static
                Tweet tweet = mTweets.get(position);            // create intent for the new activity
                Intent intent = new Intent(context, TweetDetails.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                // show the activity
                context.startActivity(intent);
            }
        }

    }


    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

}