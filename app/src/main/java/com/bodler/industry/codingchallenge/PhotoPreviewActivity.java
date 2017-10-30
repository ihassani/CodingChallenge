package com.bodler.industry.codingchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PhotoPreviewActivity extends AppCompatActivity {

    String imageURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);

        imageURL = getIntent().getStringExtra("imageURL");

        ImageView photo = (ImageView) findViewById(R.id.full_screen_photo);

        Glide.with(this).load(imageURL).into(photo);
    }
}
