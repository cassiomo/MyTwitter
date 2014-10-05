package com.codepath.apps.basictwitter.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends SherlockFragmentActivity {
	private ImageView ivProfileImage;
	private TextView tvName;
	private TextView tvdescription;
    private TextView tvFollowing;
    private TextView tvTweets;
	private TextView tvFollwers;
    private TextView tvFollowingSize;
    private TextView tvTweetsSize;
    private TextView tvFollwersSize;
    private RelativeLayout rlHeader;
	private String screenName;
	private ArrayList<Tweet> tweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
		    screenName = getIntent().getStringExtra("screenName");
		} catch(Exception e){
			e.printStackTrace();
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
        rlHeader = (RelativeLayout)findViewById(R.id.rlHeader);
		tvName = (TextView)findViewById(R.id.tvName);
		tvdescription = (TextView)findViewById(R.id.tvdescription);
        tvFollowing = (TextView)findViewById(R.id.tvFollowing);
        tvFollowingSize = (TextView) findViewById(R.id.tvFollowingSize);
        tvTweetsSize = (TextView)findViewById(R.id.tvTweetsSize);
        tvTweets = (TextView)findViewById(R.id.tvTweets);
        tvFollwersSize = (TextView)findViewById(R.id.tvFollowersSize);
		tvFollwers = (TextView)findViewById(R.id.tvFollowers);
		ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);

		Log.d("debug", screenName + "");

		TwitterApplication.getRestClient().getUserTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				tweets = Tweet.fromJsonArray(json);
				Log.d("debug", tweets.get(0).getUser().getName());
				User user = tweets.get(0).getUser();
				
				getActionBar().setTitle(user.getScreenName());
				tvName.setText(user.getName());
				tvdescription.setText(user.getDescription());
                tvTweetsSize.setText(String.valueOf(tweets.size()));
                tvTweets.setText("Tweets");
                tvFollowingSize.setText(user.getFollowing());
                tvFollowing.setText("Following");
                tvFollwersSize.setText(user.getFollowers());
				tvFollwers.setText("Followers");

                //Picasso.with(getApplicationContext()).load(user.getProfileBackgroundImageUrl()).into(target);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
     // could be in onPause or onStop
        //Picasso.with(this).cancelRequest(target);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Drawable drawImage = new BitmapDrawable(
                    getApplicationContext().getResources(), bitmap);
            rlHeader.setBackground(drawImage);
        }

        @Override
        public void onPrepareLoad(Drawable drawable) {

        }

        @Override
        public void onBitmapFailed(Drawable drawable) {

        }

    };
	
	public String getScreenName(){
		return screenName;
	}
}
