package com.codepath.apps.basictwitter.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.codepath.apps.basictwitter.fragments.HomeFragment;
import com.codepath.apps.basictwitter.fragments.MentionsFragment;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.fragments.TweetsFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.listeners.SherlockTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TimelineActivity extends SherlockFragmentActivity {

    private static int POST_TWEET_REQUEST_CODE = 10;
    private static int REPLY_TWEET_REQUEST_CODE = 20;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MUST request the feature before setting content view
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_time_line);
        showProgressBar();
        setupTabs();
        hideProgressBar();
    }

    private void setupTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("Home")
                .setIcon(R.drawable.ic_home_profile)
                .setTag("HomeFragment")
                .setTabListener(new SherlockTabListener<HomeFragment>(R.id.flTimelineContainer, this,
                        "home", HomeFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setIcon(R.drawable.ic_mentions)
                .setText("Mentions")
                .setTag("MentionsFragment")
                .setTabListener(new SherlockTabListener<MentionsFragment>(R.id.flTimelineContainer, this,
                        "mentions", MentionsFragment.class));
        actionBar.addTab(tab2);
    }

    public void onPostTweet(MenuItem mi) {
        Intent i = new Intent(this, PostTweetActivity.class);
        startActivityForResult(i, POST_TWEET_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data !=null) {
            Serializable serializable = data.getSerializableExtra("tweet");
            if (serializable != null) {
                Tweet tweet = (Tweet) serializable;
                HomeFragment homeFragment;
                switch (requestCode) {
                    case 10:
                        // Post tweet case
                        homeFragment = (HomeFragment)
                                getSupportFragmentManager().findFragmentByTag("home");
                        homeFragment.updatePostTweet(tweet);
                        break;
                    case 20:
                        homeFragment = (HomeFragment)
                                getSupportFragmentManager().findFragmentByTag("home");
                        String pos = (String) data.getStringExtra("position");
                        int replyPosition = Integer.valueOf(pos);
                        homeFragment.updateReplyCount(tweet, replyPosition);
                        // Reply case
                        break;
                    default:
                        Log.i("Default", "default");
                }
            }
        }
    }

    public void startCommentActivity(Intent i){
        startActivityForResult(i, REPLY_TWEET_REQUEST_CODE);
    }

    public void onProfileView(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                try {
                    query = java.net.URLEncoder.encode(query, "UTF-8").replace("+", "%20");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
}
