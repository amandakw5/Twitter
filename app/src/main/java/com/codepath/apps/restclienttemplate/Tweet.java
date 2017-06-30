package com.codepath.apps.restclienttemplate;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by awestort on 6/26/17.
 */
@Parcel
public class Tweet {

    // list out the attributes
    public String body;
    public long uid; // database ID for the tweet
    public String createdAt;
    public User user;
    public Entities entities;
    public String mediaUrl;

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values frm JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.entities = Entities.fromJSON(jsonObject.getJSONObject("entities"));
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        if ((jsonObject.has("entities")) && (jsonObject.getJSONObject("entities").has("media"))){
            tweet.mediaUrl = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url");
        }
        return tweet;
    }

}


