package com.codepath.apps.basictwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.fragments.HomeFragment;
import com.codepath.apps.basictwitter.fragments.MentionsFragment;
import com.codepath.apps.basictwitter.listeners.EndlessScrollListener;
import com.codepath.apps.basictwitter.listeners.SherlockTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends SherlockFragmentActivity {

    private static int REQUEST_CODE = 10;
    private ListView lvSearchTweets;
    private String query;
    private ArrayList<Tweet> searchedTweets;
    private ArrayAdapter<Tweet> aSearchedTweets;
    private TwitterClient client;
    private SwipeRefreshLayout swipeSearchContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupView();
        showProgressBar();
        try{
            query = getIntent().getStringExtra("query");
            executeQuery(query);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setupView() {
        lvSearchTweets = (ListView) findViewById(R.id.lvSearchTweets);
        searchedTweets = new ArrayList<Tweet>();
        aSearchedTweets = new TweetArrayAdapter(this, searchedTweets);
        lvSearchTweets.setAdapter(aSearchedTweets);

        swipeSearchContainer = (SwipeRefreshLayout) findViewById(R.id.swipeSearchContainer);
        // Setup refresh listener which triggers new data loading
        swipeSearchContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //populateTimeline(0);
                Log.i("debug", "testing");
            }
        });

        lvSearchTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
//                Tweet last_tweet = tweets.get(tweets.size() - 1);
//                loadMoreTweets(last_tweet.gettId() - 1);
//
//                aTweets.notifyDataSetChanged();
                Log.i("debug", "testing");
            }
        });

        lvSearchTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Log.i("debug", "testing");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onPostTweet(MenuItem mi) {
        Intent i = new Intent(this, PostTweetActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            Tweet tweet = (Tweet) data.getSerializableExtra("tweet");
            HomeFragment homeFragment = (HomeFragment)
                    getSupportFragmentManager().findFragmentByTag("home");
            homeFragment.updatePostTweet(tweet);
        }

    }

    public void onProfileView(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        Log.d("debug", tab.toString());
    }

    public void showProgressBar() {
        setProgressBarIndeterminateVisibility(true);
    }

    // Should be called when an async task has finished
    public void hideProgressBar() {
        setProgressBarIndeterminateVisibility(false);
    }

    public void executeQuery(String query) {
        TwitterApplication.getRestClient().getSearchQueryResult(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                Toast.makeText(getApplicationContext(), "Network is available -- Searching", Toast.LENGTH_LONG).show();
                Log.d("debug", json.toString());
                aSearchedTweets.addAll(Tweet.fromJsonArray(json));
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
            }

            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
            }

            @Override
            public void onSuccess(int i, JSONArray jsonArray) {
                super.onSuccess(i, jsonArray);
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
            }

            @Override
            public void onSuccess(int i, JSONObject jsonObject) {
                Log.d("debug", jsonObject.toString());

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("statuses");
                    ArrayList<Tweet> tweets = Tweet.fromJsonArray(jsonArray);
                    aSearchedTweets.addAll(tweets);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable arg0, String arg1) {
                Toast.makeText(getApplicationContext(), "Search Failed - arg1" + arg0.toString(), Toast.LENGTH_LONG).show();
                arg0.printStackTrace();
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                Toast.makeText(getApplicationContext(), "Search Failed " + jsonObject.toString(), Toast.LENGTH_LONG).show();
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(Throwable throwable, JSONArray jsonArray) {
                Toast.makeText(getApplicationContext(), "Search Failed", Toast.LENGTH_LONG).show();
                throwable.printStackTrace();
            }
        }, query, 0);
    }
}
