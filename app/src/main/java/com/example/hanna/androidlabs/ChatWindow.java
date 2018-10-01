package com.example.hanna.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private ListView listView;
    private EditText editText;
    private Button sendButton;
    private List<String> list = new ArrayList<>();
    private ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.listViewChat);
        editText = (EditText) findViewById(R.id.textChat);
        sendButton = (Button) findViewById(R.id.senButton);

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
    }

    public void onClick(View view){
        list.add(editText.getText().toString());
        messageAdapter = new ChatAdapter(this);
        messageAdapter.notifyDataSetChanged();
        editText.setText("");
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
}
