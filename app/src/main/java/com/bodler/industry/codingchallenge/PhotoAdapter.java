package com.bodler.industry.codingchallenge;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by ibrahim on 27/10/2017.
 */

public class PhotoAdapter extends BaseAdapter {

    private Context context;
    private JSONArray data;

    public PhotoAdapter(Context context, JSONArray data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.photo_item, null);
        }

        ImageView photoImageView = (ImageView)convertView.findViewById(R.id.photo_imageView);

        try {
            Log.e("position", data.getJSONObject(position).getJSONArray("images").getJSONObject(0).getString("source"));
            Glide.with(context).load(data.getJSONObject(position).getString("picture")).into(photoImageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
