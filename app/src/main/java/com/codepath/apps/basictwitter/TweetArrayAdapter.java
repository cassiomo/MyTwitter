package com.codepath.apps.basictwitter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        Tweet tweet = getItem(position);
        View v;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.tweet_item, parent, false);
        } else {
            v = convertView;
        }

        ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
        TextView tvScreenName = (TextView) v.findViewById((R.id.tvScreenName));
        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
        TextView tvCreatedAt = (TextView)v.findViewById(R.id.tvCreateAt);
        //ivProfileImage.setImageResource(android.R.color.transparent);
        ivProfileImage.setImageResource(0);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
        tvScreenName.setText("@" + tweet.getUser().getScreenName());
        tvCreatedAt.setText(tweet.getCreatedAt());
        tvName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        return v;
    }
}
