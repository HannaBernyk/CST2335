package com.example.hanna.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Intent i = getIntent();

        MessageFragment mf = new MessageFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putLong("messageId",   i.getLongExtra("messageId", 666)   );
        fragmentArgs.putString("messageText", i.getStringExtra("messageText")  );
        fragmentArgs.putBoolean("isTable", false);
        mf.setArguments(fragmentArgs);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_chat, mf);
        ft.commit();
    }
}
