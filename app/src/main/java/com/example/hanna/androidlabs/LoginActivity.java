package com.example.hanna.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        EditText loginNameEditText = (EditText) findViewById(R.id.loginName);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String savedEmail = sharedPref.getString(("DefaultEmail"), "email@domain.com");
        loginNameEditText.setText(savedEmail);

        Log.i(ACTIVITY_NAME, "In OnCreate()");
        super.onCreate(savedInstanceState);
    }

    public void loginButtonOnClick(View view){
        EditText loginNameEditText = (EditText) findViewById(R.id.loginName);

        SharedPreferences.Editor sharedPrefEditor = getPreferences(Context.MODE_PRIVATE).edit();
        String savedEmail = loginNameEditText.getText().toString();
        sharedPrefEditor.putString("DefaultEmail", savedEmail);
        sharedPrefEditor.commit();

        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In OnResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(ACTIVITY_NAME, "In OnStart()");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i(ACTIVITY_NAME, "In OnPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In OnStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In OnDestroy()");
        super.onDestroy();
    }
}
