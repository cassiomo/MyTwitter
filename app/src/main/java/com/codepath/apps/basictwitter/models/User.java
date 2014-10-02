package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kemo on 9/27/14.
 */
public class User implements Serializable {
//public class User implements Parcelable {
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

//    private User(Parcel in) {
//        uid = in.readInt();
//        name = in.readString();
//        screenName = in.readString();
//        profileImageUrl = in.readString();
//        followers = in.readString();
//        following = in.readString();
//        description = in.readString();
//    }

    public User() {
        //normal actions performed by class, it's still a normal object!
    }

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

    public static User fromUserModel(UserModel userModel) {
        User u = new User();
            u.name = userModel.getName();
            u.uid = userModel.getId();
            u.screenName = userModel.getScreenName();
            u.followers = userModel.getFollowers();
            u.following = userModel.getFollowing();
            u.description = userModel.getDescription();
            u.profileImageUrl = userModel.getProfileImageUrl();
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

    //@Override
    public int describeContents() {
        return 0;
    }

//    //@Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.name);
//        dest.writeString(this.screenName);
//        dest.writeString(this.profileImageUrl);
//        dest.writeString(this.followers);
//        dest.writeString(this.following);
//        dest.writeString(this.description);
//        dest.writeLong(this.uid);
//    }
//
//    public static final Parcelable.Creator<User> CREATOR
//            = new Parcelable.Creator<User>() {
//        @Override
//        public User createFromParcel(Parcel in) {
//            return new User(in);
//        }
//
//        @Override
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };
}
