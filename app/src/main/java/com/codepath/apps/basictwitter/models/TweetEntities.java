package com.codepath.apps.basictwitter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kemo on 10/6/14.
 */
public class TweetEntities  implements Serializable {

    private static final long serialVersionUID = 8941162947564834964L;
    private String mediaUrl = null;

    public static TweetEntities fromJson(JSONObject jsonObject) {
        TweetEntities entities = new TweetEntities();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("media");
            if (jsonArray.length() > 0) {
                JSONObject jsonObject1 = (JSONObject)jsonArray.get(0);
                entities.mediaUrl = jsonObject1.getString("media_url");
            } else {
                entities.mediaUrl = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            entities.mediaUrl = null;
            return null;
        }
        return entities;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}
