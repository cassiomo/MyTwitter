package com.codepath.apps.basictwitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kemo on 9/29/14.
 */
@Table(name = "Reply")
public class ReplyModel extends Model {

    @Column(name = "reply")
    private Tweet reply;

    public ReplyModel() {
        super();
    }

    // Parse model from JSON
    public ReplyModel(JSONObject object){
        super();

//        try {
//            this.reply = object.getJSONObject("user");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public Tweet getReply() {
        return reply;
    }

    public void setReply(Tweet reply) {
        this.reply = reply;
    }

    public List<TweetModel> replies() {
        return getMany(TweetModel.class, "ReplyModel");
    }
}
