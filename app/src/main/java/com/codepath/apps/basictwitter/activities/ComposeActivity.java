package com.codepath.apps.basictwitter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

public class ComposeActivity extends SherlockFragmentActivity {

    private Tweet parentTweet;
    private String partentTweetPosition;
    private EditText etComposeStatus;
    private ImageView ivComposeProfileImage;
    private TextView tvComposeName;
    private TextView tvComposeScreenName;
    private TwitterClient client;
    private User user;
    private com.actionbarsherlock.view.Menu menu;
    private com.actionbarsherlock.view.MenuItem iTweetLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);


        getActionBar().setDisplayShowTitleEnabled(false); // hide app name in action bar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        parentTweet = (Tweet)intent.getSerializableExtra("tweet");
        partentTweetPosition = intent.getStringExtra("position");

        etComposeStatus = (EditText)findViewById(R.id.etComposeStatus);
        etComposeStatus.setText("@" + parentTweet.getUser().getScreenName());
        tvComposeName = (TextView)findViewById(R.id.tvComposeName);
        tvComposeScreenName = (TextView)findViewById(R.id.tvComposeScreenName);
        ivComposeProfileImage = (ImageView)findViewById(R.id.ivComposeProfileImage);
        client = TwitterApplication.getRestClient();
        getUserInfo();
        etComposeStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
                Log.d("debug", 140 - etComposeStatus.length() + "");
                int length = 140 - etComposeStatus.length();

                iTweetLength = menu.findItem(R.id.iComposeTweetLength);
                iTweetLength.setTitle(length + "");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });        
        
    }

    public void getUserInfo(){
        client.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONObject json) {
                user = User.fromJson(json);
                tvComposeName.setText(user.getName());
                tvComposeScreenName.setText(user.getScreenName());

                ivComposeProfileImage.setImageResource(0);
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(user.getProfileImageUrl(), ivComposeProfileImage);
            }
            @Override
            public void onFailure(Throwable arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.d("debug", arg0.toString());
                Log.d("debug", arg1.toString());
            }
        });
    }

    public void onPostReply(com.actionbarsherlock.view.MenuItem mi){
        TwitterClient client = new TwitterClient(this);
        Tweet newTweet = new Tweet();
        newTweet.setUser(user);
        newTweet.setBody(etComposeStatus.getText().toString());
        newTweet.setCreatedAt(System.currentTimeMillis() + "");
        newTweet.setUid(1);
        newTweet.setReplyCount(1);

        client.postReply(etComposeStatus.getText().toString(), parentTweet.gettId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                Toast.makeText(getApplicationContext(), "Tweet posted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.d("debug", arg0.toString());
                Log.d("debug", arg1.toString());
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Intent data = new Intent();
        data.putExtra("tweet", newTweet);
        data.putExtra("position", partentTweetPosition);
        setResult(RESULT_OK, data);
        finish();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.compose, menu);
//        return true;
//    }

    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.compose, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
