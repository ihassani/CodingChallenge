package com.bodler.industry.codingchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class AlbumActivity extends AppCompatActivity {

    private LoginButton loginButton;

    private CallbackManager callbackManager;

    private LinearLayoutManager mLayoutManager;

    public RecyclerView albumRecyclerView;

    AlbumAdapter.OnClickItemListenner onClickItemListenner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_album);

        albumRecyclerView = (RecyclerView) findViewById(R.id.album_recycler_view);

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        albumRecyclerView.setLayoutManager(mLayoutManager);

        LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("user_photos"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me?fields=albums{name}",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                try {
                                    JSONObject albums = new JSONObject(response.getRawResponse()).getJSONObject("albums");
                                    JSONArray data = albums.getJSONArray("data");

                                    AlbumAdapter adapter = new AlbumAdapter(data);
                                    adapter.setOnClickItemListenner(new AlbumAdapter.OnClickItemListenner() {
                                        @Override
                                        public void onClick(int position, final String id) {
                                            final Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);

                                            final String albumId = id;
                                                    new GraphRequest(
                                                            AccessToken.getCurrentAccessToken(),
                                                            "/" + albumId + "/photos?fields=picture,images",
                                                            null,
                                                            HttpMethod.GET,
                                                            new GraphRequest.Callback() {
                                                                public void onCompleted(GraphResponse response) {

                                                                    try {
                                                                        JSONObject album = new JSONObject(response.getRawResponse())/*.getJSONObject("data")*/;
                                                                        JSONArray data = album.getJSONArray("data");
                                                                        intent.putExtra("albumId", data.toString());
                                                                        Log.e("onCompleted", String.valueOf(intent.hasExtra("albumId")));
                                                                        startActivity(intent);

                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    Log.e("onCompleted2", albumId + "-----" + response.getRawResponse());
                                                                }
                                                            }
                                                    ).executeAsync();
                                        }
                                    });


                                    albumRecyclerView.setAdapter(adapter);
                                    Log.e("onSuccess", data.toString());
                                    } catch (JSONException e) {
                                    e.printStackTrace();
                                    }
                            }
                        }
                ).executeAsync();



            }

            @Override
            public void onCancel() {
                Log.e("onCancele", "attempt canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("onError", "attempt failed");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
