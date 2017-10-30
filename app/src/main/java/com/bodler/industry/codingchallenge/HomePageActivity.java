package com.bodler.industry.codingchallenge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomePageActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;

    private Button newAccountButton;

    String email = null, password = null;

    private static final String TYPE_ACCOUNT = "com.bodler.industry.codingchallenge.HomePageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        newAccountButton = (Button) findViewById(R.id.new_account_button);


        SharedPreferences sp1=this.getSharedPreferences("Login",0);

        email = sp1.getString("email", null);
        password = sp1.getString("password", null);


        if(email != null && password != null) {
            emailEditText.setText(email);
//            Log.e("account", email);
            passwordEditText.setText(password);
            newAccountButton.performClick();
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState)
    {
        outState.putString("email", emailEditText.getText().toString());
        outState.putString("password",  passwordEditText.getText().toString());
    }

    public void newAccountClicked(View v) {
        if (!emailEditText.getText().toString().trim().equals("") && !passwordEditText.getText().toString().trim().equals("")) {
            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();

            SharedPreferences sp=getSharedPreferences("Login", 0);
            SharedPreferences.Editor Ed=sp.edit();

            Ed.putString("email", email);
            Ed.putString("password", password);
            Ed.commit();

            Intent intent = new Intent(this, AlbumActivity.class);
            startActivity(intent);
        }

    }
}
