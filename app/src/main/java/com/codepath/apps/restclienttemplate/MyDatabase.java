package com.codepath.apps.restclienttemplate;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String AUTHORITY = "com.codepath.myappname.provider";

    public static final String NAME = "RestClientDatabase";

    public static final int VERSION = 1;

    public static final String BASE_CONTENT_URI = "content://";

    DatabaseDefinition database = FlowManager.getDatabase(Tweet.class);

}
