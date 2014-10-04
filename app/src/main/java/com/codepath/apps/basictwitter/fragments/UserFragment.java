package com.codepath.apps.basictwitter.fragments;

//import android.app.Activity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.activities.ProfileActivity;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

public class UserFragment extends TweetsFragment {
	private TwitterClient client;
	private String screenName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		screenName = ((ProfileActivity)getActivity()).getScreenName();
		populateTimeline(0);
	}

    public void populateTimeline(long maxId){
		client.getUserTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJsonArray(json));
			}
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.d("debug", arg0.toString());
				Log.d("debug", arg1.toString());
			}
		}, maxId, screenName);
	}
}
