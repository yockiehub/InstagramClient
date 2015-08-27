package com.example.android.instagramclient;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "f4f5c24deafb45fa93ec7601ccb579fc";
    private ArrayList<InstagramPhoto> instaPics;
    private InstagramPhotosAdapter aPhotos;
    private SwipeRefreshLayout swipeContainer;
    private ListView lvPhotos;

    private AsyncHttpClient client = new AsyncHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        //SEND OUT API REQUEST to POPULAR PHOTOS
        instaPics = new ArrayList<InstagramPhoto>();
        //1. Create the adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, instaPics);
        //2. Find the listview from the layout. This cast appears to be always necessary
        lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        //3. Set the adapter binding it to the list
        lvPhotos.setAdapter(aPhotos);
        //4. Fetch the popular photos
        fetchPopularPhotos();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        //Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {

                fetchPopularPhotos();
                swipeContainer.setRefreshing(false);
            }
        });
        //Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    //Trigger API request
    public void fetchPopularPhotos() {

        String url = "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
        //Trigger the GET request
        client.get(url,null,new JsonHttpResponseHandler(){

            //A JSONObject is used instead of an array, since the response (in instagram API)
            //provides an object, and not an array
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//              Iterate each of the photo items and decode the item into a java object
                /*
                LEARNING NOTE:
                JSONArray is used in a different way than normal arrays. As we can see in the lines below
                to access a particular item of a JSONArray, we need a get method.
                */
                aPhotos.clear();

                JSONArray photoArrayJSON = null;

                try{
                    photoArrayJSON = response.getJSONArray("data");//this provides us with the array of data from instagram
                    for (int i = 0; i < photoArrayJSON.length(); i++){
                        //get the JSONObject in that position
                        JSONObject photoJSON = photoArrayJSON.getJSONObject(i);
                        //decode the attributes of the JSONObject into a data model
                        InstagramPhoto photo = new InstagramPhoto(photoJSON);
                        //Adding the decoded photos to my ArrayList of instagram photos
                        instaPics.add(photo);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

                setupListViewListener();

                //callback
                aPhotos.notifyDataSetChanged();

            }



            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                //super.onFailure(statusCode, headers, responseString, throwable);
                //When this statement was missing, I got a "explicit close method" problem, or sth like that
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        });

    }

    private void setupListViewListener() {
        lvPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(PhotosActivity.this, CommentActivity.class);
                i.putExtra("mediaId", instaPics.get(position).id);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
