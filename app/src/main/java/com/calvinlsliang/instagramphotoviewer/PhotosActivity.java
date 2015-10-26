package com.calvinlsliang.instagramphotoviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "dfdf1e6ca7364f64bed8bb308bc75513";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photos = new ArrayList<InstagramPhoto>();

        // Create the adapter
        aPhotos = new InstagramPhotosAdapter(this, photos);

        // Find ListView
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);

        lvPhotos.setAdapter(aPhotos);

        // Send out a test API request to IG photos
        fetchPopularPhotos();

    }

    public void fetchPopularPhotos() {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        AsyncHttpClient client = new AsyncHttpClient();

        // Trigger GET request
        client.get(url, null, new JsonHttpResponseHandler() {
            // onSuccess (200)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJSON = null;

                // Iterate over response and create objs for each
                try {
                    photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();

                        /*
                            public String username;
                            public String caption;
                            public String imageUrl;
                            public int imageHeight;
                            public int likesCount;
                         */
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.profileUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");

                        JSONObject image = photoJSON.getJSONObject("images").getJSONObject("standard_resolution");
                        photo.imageUrl = image.getString("url");
                        photo.imageHeight = image.getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");

                        SimpleDateFormat sdf = new SimpleDateFormat("MMM F, yyyy");
                        Long unixTime = Long.parseLong(photoJSON.getString("created_time"))*1000L;
                        Date date = new Date(unixTime);
                        String formattedDate = sdf.format(date);

                        photo.createdTime = formattedDate;


                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aPhotos.notifyDataSetChanged();
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
