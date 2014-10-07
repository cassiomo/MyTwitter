package com.codepath.apps.basictwitter.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.activities.ProfileActivity;
import com.codepath.apps.basictwitter.activities.TimelineActivity;
import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by kemo on 9/27/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        Tweet tweet = getItem(position);
        View v;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.tweet_item, parent, false);
        } else {
            v = convertView;
        }

        final ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);

        ivProfileImage.setTag(tweet.getUser().getScreenName());
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                Intent i = new Intent(inflater.getContext(), ProfileActivity.class);
                String screenName = (String)ivProfileImage.getTag();
                i.putExtra("screenName", screenName);
                v.getContext().startActivity(i);
                Log.i("debug", "testing");
            }
        });


        ImageLoader imageLoader = ImageLoader.getInstance();
        if (tweet.getFavoriteCount() !=0) {
            TextView tvFavorite = (TextView) v.findViewById(R.id.tvFavorite);
            tvFavorite.setText(String.valueOf(tweet.getFavoriteCount()));
        }
        if (tweet.getRetweetCount() !=0) {
            TextView tvRetweet = (TextView) v.findViewById(R.id.tvRetweet);
            tvRetweet.setText(String.valueOf(tweet.getRetweetCount()));
        }
        if (tweet.getMediaUrl() !=null) {
            ImageView ivBody = (ImageView) v.findViewById(R.id.ivBody);
            imageLoader.displayImage(tweet.getMediaUrl(), ivBody);
        }
        TextView tvScreenName = (TextView) v.findViewById((R.id.tvScreenName));
        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
        TextView tvCreatedAt = (TextView)v.findViewById(R.id.tvCreateAt);
        //ivProfileImage.setImageResource(android.R.color.transparent);
        ivProfileImage.setImageResource(0);
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
        tvScreenName.setText("@" + tweet.getUser().getScreenName());
        tvCreatedAt.setText(tweet.getCreatedAt());
        tvName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        return v;
    }
}
