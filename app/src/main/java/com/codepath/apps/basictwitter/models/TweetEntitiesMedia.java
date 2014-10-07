package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kemo on 10/6/14.
 */
public class TweetEntitiesMedia implements Serializable {

    private static final long serialVersionUID = -1914006189932539330L;
    public static String mediaUrl;

    public static TweetEntitiesMedia fromJson(JSONObject jsonObject) {
        TweetEntitiesMedia entitiesMedia = new TweetEntitiesMedia();
        try {
            entitiesMedia.mediaUrl = jsonObject.getString("media_url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return entitiesMedia;
    }
}