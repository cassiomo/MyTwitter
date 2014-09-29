package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kemo on 9/27/14.
 */
public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3244859823771047067L;
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String followers;
    private String following;
    private String description;

    public static User fromJson(JSONObject jsonObject) {
        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.followers = jsonObject.getString("followers_count");
            u.following = jsonObject.getString("friends_count");
            u.description = jsonObject.getString("description");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return u;
    }

    public String getDescription() {
        return description;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getFollowers() {
        return followers;
    }

    public String getFollowing() {
        return following;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
