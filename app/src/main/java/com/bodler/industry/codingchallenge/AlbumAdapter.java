package com.bodler.industry.codingchallenge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by ibrahim on 26/10/2017.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ItemRowHolder> {

    public interface OnClickItemListenner {
        public void onClick(int position,String id);
    }

    private JSONArray data;

    private OnClickItemListenner mOnClickItemListenner;

    public AlbumAdapter(JSONArray data) {
        this.data = data;
    }

    public void setOnClickItemListenner(OnClickItemListenner onClickItemListenner) {
        this.mOnClickItemListenner = onClickItemListenner;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, final int position) {
        String name = "";
        String id = "";
        try {
            name =  data.getJSONObject(position).getString("name");
            id =  data.getJSONObject(position).getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.albumName.setText(name);

        final String finalId = id;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickItemListenner.onClick(position, finalId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length();
    }


    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView albumName;





        public ItemRowHolder(View view) {
            super(view);

            this.albumName = (TextView) view.findViewById(R.id.album_name);

        }

    }
}


