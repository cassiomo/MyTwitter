package com.codepath.apps.basictwitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kemo on 9/29/14.
 */
@Table(name = "Tweet")
public class TweetModel extends Model {

    @Column(name = "tId", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long tId;

    @Column(name = "body")
    public String body;

    @Column(name = "createdAt")
    public String createdAt;

    @Column(name = "uId")
    public long uId;

    @Column(name = "user")
    public User user;

//    @Column(name = "replies", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
//    public ArrayList<Tweet> replies;

    public TweetModel() {
        super();
    }

    public TweetModel(long tId, String body, String createdAt, User user) {
        this.tId = tId;
        this.body = body;
        this.createdAt = createdAt;
        this.uId = user.getUid();
        this.user = user;
    }

    // Parse model from JSON
    public TweetModel(JSONObject object){
        super();

        try {
            this.tId = object.getLong("id");
            this.body = object.getString("body");
            this.createdAt = object.getString("createAt");
            JSONObject jsonUserObject = object.getJSONObject("user");
            User user = User.fromJson(jsonUserObject);
            this.uId = user.getUid();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public ArrayList<Tweet> getReplies() {
//        return replies;
//    }
//
//    public void addReply(Tweet reply) {
//        if (this.replies == null) {
//            this.replies = new ArrayList<Tweet>();
//        }
//        replies.add(reply);
//    }

    @Override
    public String toString() {
        return "TweetModel{" +
                "tId=" + tId +
                ", body='" + body + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", uId=" + uId +
                //", replies=" + replies +
                '}';
    }

    public static List<TweetModel> getAll() {
        return new Select()
                .from(TweetModel.class)
                .execute();
    }

    public static List<TweetModel> getRecentTweets() {
        return new Select().from(TweetModel.class).orderBy("id DESC").limit("300").execute();
    }
}
