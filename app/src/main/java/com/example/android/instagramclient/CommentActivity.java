package com.example.android.instagramclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yockie on 16/08/15.
 */
public class CommentActivity extends ActionBarActivity {
    private InstagramCommentAdapter aComments;
    private ArrayList<InstagramComment> comments;




    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        fetchComments();

    }

    private void fetchComments() {

        comments = new ArrayList<InstagramComment>();
        aComments = new InstagramCommentAdapter(this, comments);

        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(aComments);

        AsyncHttpClient client = new AsyncHttpClient();

        String url = "https://api.instagram.com/v1/media/" + getIntent().getStringExtra("mediaId") + "/comments?client_id=" + PhotosActivity.CLIENT_ID;
        client.get(url,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray commentsJSON = null;
                try {
                    commentsJSON = response.getJSONArray("data");

                    for (int i = commentsJSON.length()-2; i < commentsJSON.length(); i++) {
                        JSONObject commentJSON = commentsJSON.getJSONObject(i);     // each photo
                        InstagramComment comm = new InstagramComment(commentJSON);
                        comments.add(comm);
                    }

                    aComments.notifyDataSetChanged(); // adapter HAS TO notify the view that the update is done
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

}
