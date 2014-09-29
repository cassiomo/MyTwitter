package com.codepath.apps.basictwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.DefaultHeaderTransformer;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import org.json.JSONArray;

import java.util.ArrayList;

public class TimelineActivity extends SherlockFragmentActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;
    //private PullToRefreshLayout mPullToRefreshLayout;
    private SwipeRefreshLayout swipeContainer;

    private static int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        client = TwitterApplication.getRestClient();
        lvTweets = (ListView)findViewById(R.id.lvTweets);
//        tweets = new ArrayList<Tweet>();
//        //aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
//        aTweets = new TweetArrayAdapter(this, tweets);
//        lvTweets.setAdapter(aTweets);
//        lvTweets.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                Tweet last_tweet = tweets.get(tweets.size() - 1);
//                loadMoreTweets(last_tweet.getUid() - 1);
//
//                aTweets.notifyDataSetChanged();
//            }
//        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeline(0);
            }
        });

        populateTimeline(0);

        // Now find the PullToRefreshLayout to setup
        //mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);

        // Now setup the PullToRefreshLayout
//        ActionBarPullToRefresh.from(this)
//                // Mark All Children as pullable
////                .options(Options.create()
////                        // Here we make the refresh scroll distance to 75% of the refreshable view's height
////                        .scrollDistance(.75f)
////                                // Here we define a custom header layout which will be inflated and used
////                        .headerLayout(R.layout.)
////                                // Here we define a custom header transformer which will alter the header
////                                // based on the current pull-to-refresh state
////                        .headerTransformer(new DefaultHeaderTransformer())
////                        .build())
//                .allChildrenArePullable()
//                        // Set a OnRefreshListener
//                .listener(new OnRefreshListener() {
//                    @Override
//                    public void onRefreshStarted(View view) {
//                        populateTimeline(0);
//                    }
//                })
        // Finally commit the setup to our PullToRefreshLayout
                //.setup(mPullToRefreshLayout);

    }

    private void loadMoreTweets(long maxId) {
        populateTimeline(maxId);
    }

    public void populateTimeline(long maxId) {

        if (maxId == 0) {
            swipeContainer.setRefreshing(false);
            tweets = new ArrayList<Tweet>();
            //aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
            aTweets = new TweetArrayAdapter(this, tweets);
            lvTweets.setAdapter(aTweets);
            lvTweets.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    Tweet last_tweet = tweets.get(tweets.size() - 1);
                    loadMoreTweets(last_tweet.getUid() - 1);

                    aTweets.notifyDataSetChanged();
                }
            });
        }

        client.getHomeTimeline(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONArray json) {
                Log.d("debug", json.toString());
                aTweets.addAll(Tweet.fromJsonArray(json));

            }

            @Override
            public void onFailure(Throwable throwable, String s) {
               Log.d("debug", s.toString());
               Log.d("debug", throwable.toString());
            }
        }, maxId);
    }

    public void onPostTweet(MenuItem mi) {
        Intent i = new Intent(this, PostTweetActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
