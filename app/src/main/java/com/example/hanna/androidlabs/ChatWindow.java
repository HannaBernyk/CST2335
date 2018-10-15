package com.example.hanna.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindowActitivy";
    private ListView listView;
    private EditText editText;
    private Button sendButton;
    private List<String> list = new ArrayList<>();
    private ChatAdapter messageAdapter;

    private ChatDatabaseHelper chatDatabaseHelper;

    //load messages from DB by using sursor
    private void onCreate(){
        chatDatabaseHelper = new ChatDatabaseHelper(getApplicationContext());
        Cursor cursor = chatDatabaseHelper.getReadableDatabase().query(ChatDatabaseHelper.MESSAGE_TABLE_NAME,
                new String[] {ChatDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount() );
            list.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        cursor.close();
    }

    //when I open chat window - messages are loaded and shown, they are read from DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreate();
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.listViewChat);
        editText = (EditText) findViewById(R.id.textChat);
        sendButton = (Button) findViewById(R.id.senButton);

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
    }

    public void onClick(View view){
        list.add(editText.getText().toString());
        writeToDB(editText.getText().toString());
        messageAdapter = new ChatAdapter(this);
        messageAdapter.notifyDataSetChanged();
        editText.setText("");
    }

    //saves to DB, when I click on Send button it saves to DB

    private void writeToDB(String message){
        ContentValues values = new ContentValues();
        values.put(ChatDatabaseHelper.KEY_MESSAGE, message);
        long insertId = chatDatabaseHelper.getWritableDatabase().insert(ChatDatabaseHelper.MESSAGE_TABLE_NAME, null,
                values);
        Cursor cursor = chatDatabaseHelper.getWritableDatabase().query(ChatDatabaseHelper.MESSAGE_TABLE_NAME,
                new String[]{ChatDatabaseHelper.KEY_MESSAGE}, ChatDatabaseHelper.KEY_ID+ " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
    }

    private class ChatAdapter extends ArrayAdapter<String>{
        ChatAdapter(Context context){
            super(context, 0);
        }

        public int getCount(){
            return list.size();
        }

        public String getItem(int position){
            return list.get(position);
        }

        @NonNull
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;

            if(position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));

            return result;
        }

        public long getItemId(int position){
            return position;
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "Destroy activity. Close Database Connection.");
        chatDatabaseHelper.close();
        super.onDestroy();
    }
}
