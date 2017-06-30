package com.codepath.apps.restclienttemplate;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by awestort on 6/29/17.
 */
@Parcel
public class Entities {

    public String imageUrl;


    public static Entities fromJSON(JSONObject json) throws JSONException {
        Entities entities = new Entities();
       // entities.imageUrl = json.getJSONArray("media").getJSONObject(0).getString("media_url");
        //entities.urls = json.getJSONArray("urls");
        return entities;
    }
}
