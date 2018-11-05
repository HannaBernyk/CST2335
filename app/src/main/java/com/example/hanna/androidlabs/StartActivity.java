package com.example.hanna.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In OnCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startActivityButton = (Button) findViewById(R.id.buttonStartActivity);
    }


    public void onClickStartChatButton(View view){
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
        Intent intent = new Intent(StartActivity.this, ChatWindow.class);
        startActivityForResult(intent, 50);
    }

    public void onClickWeatherForecast(View view){
        Log.i(ACTIVITY_NAME, "User clicked Weather Forecast");
        Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
        startActivityForResult(intent, 50);
    }

    public void startButtonClickHandler(View view){
        Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
        startActivityForResult(intent, 50);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        if(requestCode == 50 && resultCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");

            Context context = getApplicationContext();

            Toast toast = Toast.makeText(context, messagePassed,  Toast.LENGTH_LONG);
            toast.show();
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
