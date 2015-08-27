package com.example.android.instagramclient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yockie on 15/08/15.
 */
public class InstagramComment {
    public String user_name;
    public String comment;
    public String profile_pic;

    public InstagramComment(){};

    public InstagramComment(JSONObject commentJSON) throws JSONException{
        this.user_name = commentJSON.getJSONObject("from").getString("username");
        this.comment = commentJSON.getString("text");
        this.profile_pic = commentJSON.getJSONObject("from").getString("profile_picture");
    };

}
