package com.codepath.apps.basictwitter.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetDetailActivity extends Activity {

    private Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        tweet = (Tweet)getIntent().getSerializableExtra("tweet");
        ImageLoader imageLoader = ImageLoader.getInstance();

        if (tweet.getFavoriteCount() !=0) {
            TextView tvDetailFavorite = (TextView) findViewById(R.id.tvDetailFavorite);
            tvDetailFavorite.setText(String.valueOf(tweet.getFavoriteCount()));
        }
        if (tweet.getRetweetCount() !=0) {
            TextView tvDetailRetweet = (TextView) findViewById(R.id.tvDetailRetweet);
            tvDetailRetweet.setText(String.valueOf(tweet.getRetweetCount()));
        }
        if (tweet.getMediaUrl() !=null) {
            ImageView ivDetailBody = (ImageView) findViewById(R.id.ivDetailBody);
            imageLoader.displayImage(tweet.getMediaUrl(), ivDetailBody);
        }
        TextView tvDetailScreenName = (TextView) findViewById((R.id.tvDetailScreenName));
        TextView tvDetailName = (TextView) findViewById(R.id.tvDetailName);
        TextView tvDetailBody = (TextView) findViewById(R.id.tvDetailBody);
        TextView tvDetailCreatedAt = (TextView)findViewById(R.id.tvDetailCreateAt);

        final ImageView ivDetailProfileImage = (ImageView) findViewById(R.id.ivDetailProfileImage);

        ivDetailProfileImage.setTag(tweet.getUser().getScreenName());
        //ivProfileImage.setImageResource(android.R.color.transparent);
        ivDetailProfileImage.setImageResource(0);
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivDetailProfileImage);
        tvDetailScreenName.setText("@" + tweet.getUser().getScreenName());
        tvDetailCreatedAt.setText(tweet.getCreatedAt());
        tvDetailName.setText(tweet.getUser().getName());
        tvDetailBody.setText(tweet.getBody());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweet_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
