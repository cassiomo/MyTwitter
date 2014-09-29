package com.codepath.apps.basictwitter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

public class TimelineActivity extends SherlockFragmentActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;

    private static int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        client = TwitterApplication.getRestClient();
        populateTimeline(0);
        lvTweets = (ListView)findViewById(R.id.lvTweets);
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

    private void loadMoreTweets(long maxId) {
        populateTimeline(maxId);
    }

    public void populateTimeline(long maxId) {
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
