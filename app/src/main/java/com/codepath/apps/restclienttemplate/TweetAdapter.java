package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by awestort on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    List<Tweet> mTweets;

    public void setmTweets(List<Tweet> mTweets) {
        this.mTweets = mTweets;
    }

    Context context;
    private final int REQUEST_CODE = 20;
    private TweetAdapterListener mListener;
    TwitterClient client;

    public interface TweetAdapterListener {
        public void onItemsSelected(View view, int position);
    }

    // pass in the tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener) {
        mTweets = tweets;
        mListener = listener;
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
        final int positionm = position;

        // get the data according to position
        Tweet tweet = mTweets.get(position);

        // populate the views according to this data
        holder.tvUserName.setText("@" + tweet.user.screenName);
        holder.tvBody.setText(tweet.body);
        holder.tvName.setText(tweet.user.name);
        holder.tvCreated.setText(getRelativeTimeAgo(tweet.createdAt));

        if (tweet.numFavorites != 0){
            holder.numFavorites.setText(tweet.numFavorites + "");
        }
        if (tweet.numRetweets != 0) {
            holder.numRetweets.setText(tweet.numRetweets + "");
        }
        // set circle bitmap

        Glide.with(context).load(tweet.user.profileImageUrl).transform(new CircleTransform(context)).into(holder.ivProfileImage);
        if (tweet.favorited){
            Glide.with(context).load(R.drawable.hearted).into(holder.tlFavorite);
        }
        else {
            Glide.with(context).load(R.drawable.heart).into(holder.tlFavorite);
        }
        if (tweet.retweeted){
            Glide.with(context).load(R.drawable.ic_vector_retweet).into(holder.tlRetweet);
        }
        else{
            Glide.with(context).load(R.drawable.retweet).into(holder.tlRetweet);
        }
        if (tweet.mediaUrl != null){
            Glide.with(context).load(tweet.mediaUrl).into(holder.tlMedia);
        }
        else{
            holder.tlMedia.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount(){
        return mTweets.size();
    }

    // create ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
        @BindView(R.id.tvUserName)  TextView tvUserName;
        TwitterClient client;
        @BindView(R.id.tvBody)  TextView tvBody;
        @BindView(R.id.tvName)  TextView tvName;
        @BindView(R.id.tvCreated)  TextView tvCreated;
        @BindView(R.id.dtreply)  ImageView dtreply;
        @BindView(R.id.tlFavorite)  ImageView tlFavorite;
        @BindView(R.id.tlRetweet)  ImageView tlRetweet;
        @BindView(R.id.tlMedia)  ImageView tlMedia;
        @BindView(R.id.numFavorites)  TextView numFavorites;
        @BindView(R.id.numRetweets)  TextView numRetweets;

        public ViewHolder(View itemView) {
            super(itemView);
            // perform findViewById lookups
            ButterKnife.bind(this, itemView);
            client = TwitterApp.getRestClient();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    if (mListener != null){
                        //get the position of row element
                        int position = getAdapterPosition();
                        // fire the listnener callback
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
            });

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
            tlFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Tweet tweet = mTweets.get(position);
                    if (tweet.favorited) {
                        Glide.with(context).load(R.drawable.heart).into(tlFavorite);
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
                        numFavorites.setText((tweet.numFavorites -1)+"");

                    } else {
                        Glide.with(context).load(R.drawable.hearted).into(tlFavorite);
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
                        numFavorites.setText((tweet.numFavorites + 1)+"");
                    }
                }
            });
            tlRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Tweet tweet = mTweets.get(position);
                    if (tweet.retweeted) {
                        Glide.with(context).load(R.drawable.ic_vector_retweet_stroke).into(tlRetweet);
                        client.unretweetTweet(tweet.uid, new JsonHttpResponseHandler() {
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
                        tweet.retweeted = false;
                        numRetweets.setText((tweet.numRetweets - 1) +"");
                    } else {
                        Glide.with(context).load(R.drawable.ic_vector_retweet).into(tlRetweet);
                        client.retweetTweet(tweet.uid, new JsonHttpResponseHandler() {
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
                        tweet.retweeted = true;
                        numRetweets.setText((tweet.numRetweets + 1) +"");
                    }
                }
            });

            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Tweet tweet = mTweets.get(position);
                   // User user = tweet.user;
                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra("screenName", tweet.user.screenName);
                    ((TimelineActivity) context).startActivity(i);
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
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