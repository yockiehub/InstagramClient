package com.example.android.instagramclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yockie on 13/08/15.
 */
public class InstagramPhoto {
    public String id;
    public String user_name;
    public String caption;
    public String imageURL;
    public String profileImageURL;
    public int imageHeight;
    public int likesCount;
    public int commentCount;
    public ArrayList<InstagramComment> comments;
    public long createdTime;

    public InstagramPhoto(){};

    public InstagramPhoto(JSONObject photoJSON) throws JSONException {

        this.id = photoJSON.getString("id");
        this.user_name = photoJSON.getJSONObject("user").getString("username");
        //Caption:{"data" => [x] => "caption" => "text"}
        if (!photoJSON.isNull("caption")) {
            this.caption = photoJSON.getJSONObject("caption").getString("text");
        }
        //imageURL: {"data" => [x] => "images" => "standard_resolution" => "url" }
        this.imageURL = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
        //profileImageURL: {"data" => [x] => "images" => "standard_resolution" => "url" }
        this.profileImageURL = photoJSON.getJSONObject("user").getString("profile_picture");
        //ImageHeight: {"data" => [x] => "images" => "standard_resolution" => "height" }
        this.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
        //LikesCount: { "data" => [x] => "likes" => "count" }
        this.likesCount = photoJSON.getJSONObject("likes").getInt("count");
        //CommentCount: {"data" => [x] => "comments" => "count"}
        this.commentCount = photoJSON.getJSONObject("comments").getInt("count");
        //CreatedTime: {"data" => [x] => "created_time"}
        this.createdTime = photoJSON.getLong("created_time");






        int numComments = this.commentCount >= 2 ? 2 : this.commentCount;
        this.comments = new ArrayList<InstagramComment>();
        for (int i = 0; i < numComments; i++){
            InstagramComment comment = new InstagramComment(photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(i));
            this.comments.add(comment);
        }


    }

}
