package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ReplyTweet extends AppCompatActivity {

    TwitterClient client;
    EditText typeMessageToPost;
    TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = TwitterApp.getRestClient();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_tweet);
    }
    public void onSubmit(View v) {
        final EditText etName = (EditText) findViewById(R.id.tweetbox);
        // Prepare data intent
        client.sendTweet(etName.getText().toString(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
                try {
                    Tweet t = Tweet.fromJSON(response);
                    Intent data = new Intent();
                    data.putExtra("twee", Parcels.wrap(t));
                    // Pass relevant data back as a result
                    data.putExtra("entered", etName.getText().toString());
                    data.putExtra("code", 200); // ints work too
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes the activity, pass data to parent
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    }
}
