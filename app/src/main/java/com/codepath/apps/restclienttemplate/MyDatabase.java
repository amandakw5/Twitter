package com.codepath.apps.restclienttemplate;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String AUTHORITY = "com.codepath.myappname.provider";

    public static final String NAME = "RestClientDatabase";

    public static final int VERSION = 1;

    public static final String BASE_CONTENT_URI = "content://";


    DatabaseDefinition database = FlowManager.getDatabase(TweetAdapter.class);
    List<Tweet> tweetList = SQLite.select().
            from(Tweet.class).queryList();


}
