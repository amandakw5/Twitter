package com.codepath.apps.restclienttemplate;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import com.codepath.apps.restclienttemplate.fragments.SearchTweetsFragment;

public class SearchActivity extends AppCompatActivity {
    public SearchView searchQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchQuery = (SearchView) findViewById(R.id.thesearch);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.flContainer, new SearchTweetsFragment(), "SOMETAG").
                    commit();
            // Now later we can lookup the fragment by tag
            SearchTweetsFragment fragmentDemo = (SearchTweetsFragment)
                    getSupportFragmentManager().findFragmentByTag("SOMETAG");
        }
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.thesearch);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search View");

    }
    public void onQueryTextSubmit () {

    }


    public boolean onQueryTextSubmit(String query) {

        return false;
    }


    public boolean onQueryTextChange(String newText) {
        String text = newText;
        //TweetAdapter.filter(text);
        return false;
    }
}
