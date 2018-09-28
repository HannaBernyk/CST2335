package com.example.hanna.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In OnCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startActivityButton = (Button) findViewById(R.id.buttonStartActivity);
    }

    public void startButtonClickHandler(View view){
        Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
        startActivityForResult(intent, 50);
    }

    public void onActivityResult(int responseCode, Intent data){
        if(responseCode == 50){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
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
