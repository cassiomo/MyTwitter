package com.codepath.apps.basictwitter.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;

import java.util.ArrayList;

public class ProfileActivity extends SherlockFragmentActivity {
	private ImageView ivProfileImage;
	private TextView tvName;
	private TextView tvdescription;
	private TextView tvFollwers;
	private String screenName;
	private ArrayList<Tweet> tweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			   screenName = getIntent().getStringExtra("screenName");			   
		} catch(Exception e){
			
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		tvName = (TextView)findViewById(R.id.tvName);
		tvdescription = (TextView)findViewById(R.id.tvdescription);
		tvFollwers = (TextView)findViewById(R.id.tvFollwers);
		ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
		
		Log.d("debug", screenName + "");
		
//		TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler(){
//			@Override
//			public void onSuccess(JSONObject json) {
//				User user = User.fromJson(json);
//				getActionBar().setTitle(user.getScreenName());
//				tvName.setText(user.getName());
//				tvdescription.setText(user.getDescription());
//				tvFollwers.setText(user.getFollowers() + " Followers    " + user.getFollowing() + " Following" );
//				
//				ivProfileImage.setImageResource(android.R.color.transparent);
//			    ImageLoader imageLoader = ImageLoader.getInstance();
//			    imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);
//			}
//			@Override
//			public void onFailure(Throwable arg0, String arg1) {
//				// TODO Auto-generated method stub
//				Log.d("debug", arg0.toString());
//				Log.d("debug", arg1.toString());				
//			}
//		});		
		TwitterApplication.getRestClient().getUserTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				tweets = Tweet.fromJsonArray(json);
				Log.d("debug", tweets.get(0).getUser().getName() + "ppppp");
				User user = tweets.get(0).getUser();
				
				getActionBar().setTitle(user.getScreenName());
				tvName.setText(user.getName());
				tvdescription.setText(user.getDescription());
				tvFollwers.setText(user.getFollowers() + " Followers    " + user.getFollowing() + " Following" );
				
				ivProfileImage.setImageResource(0);
			    ImageLoader imageLoader = ImageLoader.getInstance();
			    imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);
			}
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.d("debug", arg0.toString());
				Log.d("debug", arg1.toString());
			}
		}, 0, screenName);
		
	}
	
	public String getScreenName(){
		return screenName;
	}
}
