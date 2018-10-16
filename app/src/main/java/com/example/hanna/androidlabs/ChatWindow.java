package com.example.hanna.androidlabs;

import android.app.Activity;
import android.content.Context;
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
    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;
    private List<String> messages = new ArrayList<>();
    private ChatAdapter chatAdapter;

    private ChatDatabaseHelper chatDatabaseHelper;

    //when I open chat window - messages are loaded and shown, they are read from DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate");

        chatListView = (ListView) findViewById(R.id.listViewChat);
        messageEditText = (EditText) findViewById(R.id.textChat);
        sendButton = (Button) findViewById(R.id.senButton);

        chatDatabaseHelper = new ChatDatabaseHelper(this);
        messages = chatDatabaseHelper.getAllMessages();

        chatAdapter = new ChatAdapter(this);
        chatListView.setAdapter(chatAdapter);

    }

    public void onClick(View view){
        final String message = messageEditText.getText().toString();
        if(!message.isEmpty()){
            messages.add(message);
            chatDatabaseHelper.insertMessage(message);
            chatAdapter.notifyDataSetChanged();
            messageEditText.setText("");
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
