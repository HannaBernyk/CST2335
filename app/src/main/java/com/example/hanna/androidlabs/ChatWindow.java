package com.example.hanna.androidlabs;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.hanna.androidlabs.ChatDatabaseHelper.COLUMN_ALL;
import static com.example.hanna.androidlabs.ChatDatabaseHelper.KEY_ID;
import static com.example.hanna.androidlabs.ChatDatabaseHelper.KEY_MESSAGE;
import static com.example.hanna.androidlabs.ChatDatabaseHelper.MESSAGE_TABLE_NAME;

public class ChatWindow extends FragmentActivity {
    private static final String TAG = ChatDatabaseHelper.class.getName();

    protected static final String ACTIVITY_NAME = "ChatWindowActitivy";
    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;
    private List<String> messages = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private boolean isMessageDetailsFrameLayoutExists;
    private FrameLayout messageDetailsFrame;
    private Cursor dbCursor;
    private ChatDatabaseHelper chatDatabaseHelper;

    //when I open chat window - messages are loaded and shown, they are read from DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate");

        chatListView = findViewById(R.id.listViewChat);
        messageEditText = findViewById(R.id.textChat);
        sendButton = findViewById(R.id.senButton);
        messageDetailsFrame =  findViewById(R.id.frame_chat);
        if(messageDetailsFrame != null){
            isMessageDetailsFrameLayoutExists = true;
        }

        chatDatabaseHelper = new ChatDatabaseHelper(this);
        messages = getAllMessages();

        chatAdapter = new ChatAdapter(this);
        chatListView.setAdapter(chatAdapter);
        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isMessageDetailsFrameLayoutExists) {
                    MessageFragment messageFragment = new MessageFragment();
                    Bundle argumentsToPass = new Bundle();
                    argumentsToPass.putLong("messageId", id);
                    argumentsToPass.putString("messageText", messages.get(position));
                    argumentsToPass.putBoolean("isTablet", true);
                    messageFragment.setArguments(argumentsToPass);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame_chat, messageFragment);
                    ft.commit();
                }
                else {
                    Intent i = new Intent(ChatWindow.this, MessageDetails.class);
                    i.putExtra("messageId", id);
                    i.putExtra("messageText", messages.get(position));
                    startActivityForResult(i, 11);
                }
            }
        });
    }

    public List<String> getAllMessages(){
        final List<String> messages = new ArrayList<>();
        final SQLiteDatabase db = chatDatabaseHelper.getWritableDatabase();

        final Cursor cursor = db.query(MESSAGE_TABLE_NAME, COLUMN_ALL, null, null, null, null, null);
        Log.i(TAG, "Coursor's column count: " + cursor.getColumnCount());

        final int columnIdIndex = cursor.getColumnIndex(KEY_ID);
        final int columnMessageIndex = cursor.getColumnIndex(KEY_MESSAGE);

        Log.i(TAG, "Cursor's Column: " + cursor.getColumnName(columnIdIndex));
        Log.i(TAG, "Cursor's Column: " + cursor.getColumnName(columnMessageIndex));

        this.dbCursor = cursor;

        while (cursor.moveToNext()){
            final String message = cursor.getString(columnMessageIndex);
            Log.i(TAG, "SQL message: " + message);
            messages.add(message);
        }


        return messages;
    }

    public void insertMessage(String message) {
        final SQLiteDatabase db = chatDatabaseHelper.getWritableDatabase();

        final ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message);
        long insertId = db.insert(ChatDatabaseHelper.MESSAGE_TABLE_NAME, null, values);

        if (insertId > 0) {
            Log.i(TAG, "Message inserted successfully");
        }
        else {
            Log.e(TAG, "Failed to insert message");
        }
    }


    public void deleteMessage(Long id){
        final SQLiteDatabase db = chatDatabaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + ChatDatabaseHelper.MESSAGE_TABLE_NAME + " WHERE _id = " + id);

        messages = new ArrayList<>();
        messages = getAllMessages();
        chatAdapter.notifyDataSetChanged();
    }

    public void onClick(View view){
        final String message = messageEditText.getText().toString();
        if(!message.isEmpty()){
            messages.add(message);
            insertMessage(message);
            getAllMessages();
            chatAdapter.notifyDataSetChanged();
            messageEditText.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                Long messageID = data.getLongExtra("delete", -1);
                deleteMessage(messageID);
                Log.i(TAG, "Deleted: " + messageID);
            }
        }
    }

    private class ChatAdapter extends ArrayAdapter<String>{
        ChatAdapter(Context context){
            super(context, 0);
        }

        public int getCount(){
            return messages.size();
        }

        public String getItem(int position){
            return messages.get(position);

        }

        public long getItemId(int position){
            dbCursor.moveToPosition(position);
            return dbCursor.getInt(dbCursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));

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
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "Destroy activity. Close Database Connection.");
        dbCursor.close();
        chatDatabaseHelper.close();
        super.onDestroy();
    }
}
