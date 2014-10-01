package com.codepath.apps.basictwitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by kemo on 9/27/14.
 */
@Table(name = "User")
public class UserModel extends Model {

    @Column(name = "uId", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uId;

    @Column(name = "body")
    private String name;

    @Column(name = "screenName")
    private String screenName;

    @Column(name = "profileImageUrl")
    private String profileImageUrl;

    @Column(name = "followers")
    private String followers;

    @Column(name = "following")
    private String following;

    @Column(name = "description")
    private String description;

	public UserModel() {
		super();
	}

	// Parse model from JSON
	public UserModel(JSONObject jsonObject){
		super();

		try {
            this.name = jsonObject.getString("name");
            this.uId = jsonObject.getLong("id");
            this.screenName = jsonObject.getString("screen_name");
            this.followers = jsonObject.getString("followers_count");
            this.following = jsonObject.getString("friends_count");
            this.description = jsonObject.getString("description");
            this.profileImageUrl = jsonObject.getString("profile_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

    // Parse model from JSON
    public UserModel(User user){
        super();

         this.name = user.getName();
         this.uId = user.getUid();
         this.screenName = user.getScreenName();
         this.followers = user.getFollowers();
         this.following = user.getFollowing();
         this.description = user.getDescription();
         this.profileImageUrl = user.getProfileImageUrl();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Record Finders
	public static UserModel byId(long id) {
		return new Select().from(UserModel.class).where("id = ?", id).executeSingle();
	}

	public static List<UserModel> getRecentUser() {
		return new Select().from(UserModel.class).orderBy("id DESC").limit("300").execute();
	}
}
