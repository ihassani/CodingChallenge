package com.bodler.industry.codingchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;

public class PhotoActivity extends AppCompatActivity {

    private GridView photoGridView;
    String albumId = "";

    JSONArray album = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        albumId = getIntent().getStringExtra("albumId");
        Log.e("onCreate", "album id -----" + albumId);
        setContentView(R.layout.activity_photo);

        photoGridView = (GridView) findViewById(R.id.photo_gridview);

        try {
            album = new JSONArray(albumId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PhotoAdapter adapter = new PhotoAdapter(this, album);
        photoGridView.setAdapter(adapter);

        photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PhotoPreviewActivity.class);

                try {
                    intent.putExtra("imageURL", album.getJSONObject(position).getJSONArray("images").getJSONObject(0).getString("source"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(intent);
            }
        });
    }
}
