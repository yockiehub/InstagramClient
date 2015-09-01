package com.example.android.instagramclient;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private SwipeRefreshLayout commentsSwipe;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        commentsSwipe = (SwipeRefreshLayout) findViewById(R.id.commentsSwipe);
        //Setup refresh listener which triggers new data loading
        commentsSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {

                fetchComments();
                commentsSwipe.setRefreshing(false);
            }
        });
        //Configure the refreshing colors
        commentsSwipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchComments();

    }

    private void fetchComments() {

        comments = new ArrayList<InstagramComment>();
        aComments = new InstagramCommentAdapter(this, comments);

        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(aComments);

        AsyncHttpClient client = new AsyncHttpClient();

        String url = "https://api.instagram.com/v1/media/" + getIntent().getStringExtra("mediaId") + "/comments?client_id=" + PhotosActivity.CLIENT_ID;
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                comments.clear();
                JSONArray commentsJSON = null;
                try {
                    commentsJSON = response.getJSONArray("data");
                    for (int i = 0; i < commentsJSON.length(); i++) {
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
