package com.codepath.apps.basictwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.codepath.apps.basictwitter.fragments.HomeFragment;
import com.codepath.apps.basictwitter.fragments.MentionsFragment;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.fragments.TweetsFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.listeners.SherlockTabListener;
import com.codepath.apps.basictwitter.models.Tweet;

import java.util.ArrayList;

public class TimelineActivity extends SherlockFragmentActivity {

    private static int REQUEST_CODE = 10;
//    private com.actionbarsherlock.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        setupTabs();
        showProgressBar();
    }

    private void setupTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("Home")
                .setTag("HomeFragment")
                .setTabListener(new SherlockTabListener<HomeFragment>(R.id.flContainer, this,
                        "home", HomeFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Mentions")
                .setTag("MentionsFragment")
                .setTabListener(new SherlockTabListener<MentionsFragment>(R.id.flContainer, this,
                        "mentions", MentionsFragment.class));
//                .setTabListener(new FragmentTabListener<MentionsFragment>(R.id.flContainer, this,
//                        "mentions", MentionsFragment.class));
        actionBar.addTab(tab2);
        getSupportFragmentManager().executePendingTransactions();

    }

    public void onPostTweet(MenuItem mi) {
        Intent i = new Intent(this, PostTweetActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
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
