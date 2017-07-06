package com.codepath.apps.restclienttemplate;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by awestort on 6/26/17.
 */
@Parcel(analyze={Tweet.class})
@Table(database = MyDatabase.class)
public class Tweet extends BaseModel{
    @Column
    @PrimaryKey
    public long uid;

    @Column
    public String body;
    @Column
    public String createdAt;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    public User user;
    @Column
    public String mediaUrl;
    @Column
    public boolean favorited;
    @Column
    public boolean retweeted;

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values frm JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.retweeted = jsonObject.getBoolean("retweeted");
        if ((jsonObject.has("entities")) && (jsonObject.getJSONObject("entities").has("media"))){
            tweet.mediaUrl = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url");
        }
        return tweet;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }
}


