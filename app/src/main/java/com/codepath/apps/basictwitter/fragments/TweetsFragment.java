package com.codepath.apps.basictwitter.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.widget.SearchView;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.activities.ProfileActivity;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.listeners.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TweetsFragment extends SherlockFragment {

	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private TwitterClient client;
    private SwipeRefreshLayout swipeContainer;
    private com.actionbarsherlock.widget.SearchView searchView;

    public static TweetsFragment newInstance(int someInt, String someTitle) {
        TweetsFragment tweetsFragment = new TweetsFragment();
        return tweetsFragment;
    }

    public void updatePostTweet(Tweet tweet) {
        tweets.add(0, tweet);
        aTweets.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
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
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {		    	
		    	Tweet last_tweet = tweets.get(tweets.size() - 1);
		    	loadMoreTweets(last_tweet.gettId() - 1);
		    	
		    	aTweets.notifyDataSetChanged();
		    }			
	    });
		
		lvTweets.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                                
            	Tweet tweet = tweets.get(position);
            	Intent i = new Intent(inflater.getContext(), ProfileActivity.class);
            	i.putExtra("screenName", tweet.getUser().getScreenName()); 
        		startActivity(i);
                
            }
        });

//        searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // perform query here
//                //executeQuery(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}
	
	public void  addAll(ArrayList<Tweet> tweets){
		aTweets.addAll(tweets);
	}

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) (getActivity().getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //boolean isWiFi = activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void populateTimeline(long maxId) {

        if (maxId == 0) {
            swipeContainer.setRefreshing(false);
            tweets = new ArrayList<Tweet>();
            //aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
            aTweets = new TweetArrayAdapter(getActivity(), tweets);
            lvTweets.setAdapter(aTweets);
            lvTweets.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    Tweet last_tweet = tweets.get(tweets.size() - 1);
                    loadMoreTweets(last_tweet.gettId() - 1);

                    aTweets.notifyDataSetChanged();
                }
            });
        }
        boolean isNetworkAvailable = isNetworkAvailable();
        if (isNetworkAvailable) {
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
                    Toast.makeText((getActivity().getApplicationContext()), "Network is not available", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Throwable throwable, JSONObject jsonObject) {
                    Toast.makeText((getActivity().getApplicationContext()), "Network is not available", Toast.LENGTH_LONG).show();
                }
            }, maxId);
        } else {
            // read from DB.
            List<Tweet> tweets = Tweet.readTweetsFromDB();
            if (tweets != null || tweets.size() > 0) {
                aTweets.addAll(tweets);
            } else {
                Toast.makeText((getActivity().getApplicationContext()), "No Tweets from DB", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadMoreTweets(long maxId) {
        populateTimeline(maxId);
    }
	
	public void addTweet() {
		Log.d("debug", "update tweets list");
	}
	
}
