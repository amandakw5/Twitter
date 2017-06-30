package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by awestort on 6/29/17.
 */

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder>{
    List<User> users;
    Context context;

    public FollowAdapter(ArrayList<User> follows) {
        users = follows;

    }
    public FollowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View followView = inflater.inflate(R.layout.item_follow, parent, false);
        FollowAdapter.ViewHolder viewHolder = new FollowAdapter.ViewHolder(followView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FollowAdapter.ViewHolder holder, int position) {
        // get the data according to position
        User user = users.get(position);

        // populate the views according to this data
        holder.fvUserName.setText("@" + user.screenName);
        holder.fvName.setText(user.name);
        Glide.with(context).load(user.profileImageUrl).transform(new CircleTransform(context)).into(holder.fvProfilePicture);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView fvProfilePicture;
        public TextView fvUserName;
        TwitterClient client;
        public TextView fvBio;
        public TextView fvName;

        public ViewHolder(View itemView) {
            super(itemView);
            fvProfilePicture = (ImageView) itemView.findViewById(R.id.fvProfilePicture);
            fvUserName = (TextView) itemView.findViewById(R.id.fvUserName);
            fvBio = (TextView) itemView.findViewById(R.id.fvBio);
            fvName = (TextView) itemView.findViewById(R.id.fvName);
        }
    }
}
