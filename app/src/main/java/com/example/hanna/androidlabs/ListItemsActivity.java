package com.example.hanna.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In OnCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        Switch activitySwitch = (Switch) findViewById(R.id.switch1);
        activitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getApplicationContext();
                CharSequence text = isChecked ? "Switch On!" : "Switch Off!";
                int duration = isChecked ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            });
    }

    public void onClick(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 50);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 50 && resultCode == RESULT_OK) {
            ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }

    public void setOnCheckedChanged(){

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
