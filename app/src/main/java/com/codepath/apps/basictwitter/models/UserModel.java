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
@Table(name = "user")
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
	public UserModel(JSONObject object){
		super();

		try {
			this.name = object.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
