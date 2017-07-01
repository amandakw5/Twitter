package com.codepath.apps.restclienttemplate;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {

    public static final String NAME = "RestClientDatabase";

    public static final int VERSION = 1;

//    DatabaseDefinition database = FlowManager.getDatabase(TweetAdapter.class);
//    List<TweetAdapter> mtweets = SQLite.select().
//            from(TweetAdapter.class).queryList();
//
//
//
//        @Table(database = MyDatabase.class)
//        class User extends BaseModel {
//
//            @Column
//            @PrimaryKey
//            long uid;
//
//            @Column
//            String screenName;
//
//            @Column
//            String name;
//
//            @Column
//            String profileImageUrl;
//
//            @Column
//            int followers_count;
//
//            @Column
//            int following;
//
//            public void setUid(long uid) {
//                this.uid = uid;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public void setprofileImageUrl(String profileImageUrl) {
//                this.profileImageUrl = profileImageUrl;
//            }
//
//            public void setFollowers_count(int followers_count) {
//                this.followers_count = followers_count;
//            }
//
//            public void setFollowing(int following) {
//                this.following = following;
//            }
//
//            public void setScreenName(String screenName) {
//                this.screenName = screenName;
//            }
//        }
//    }
}