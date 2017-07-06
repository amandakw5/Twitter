package com.codepath.apps.restclienttemplate;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by awestort on 6/26/17.
 */

@Parcel(analyze={User.class})
@Table(database = MyDatabase.class)
public class User extends BaseModel {

    // list the attributes
    @Column
    @PrimaryKey
    public long uid;

    @Column
    public String screenName;

    @Column
    public String name;

    @Column
    public String profileImageUrl;

    @Column
    public int followers_count;

    @Column
    public int following;

    @Column
    public String bio;

    // deserialize the JSON
    public static User fromJSON(JSONObject json) throws JSONException {
        User user = new User();

        // extract and fill the values
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = (json.getString("profile_image_url")).replace("_normal","");
        user.followers_count = json.getInt("followers_count");
        user.following = json.getInt("friends_count");
        user.bio = json.getString("description");
        return user;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
