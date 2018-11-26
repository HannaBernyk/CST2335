package com.example.hanna.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {
    private Bundle messageBundle;
    private Context parent;
    private boolean isTablet = false;
    private TextView messageId;
    private TextView messageText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messageBundle = this.getArguments();
        isTablet = messageBundle.getBoolean("isTablet");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View messageDetailsView = inflater.inflate(R.layout.fragment_message_details, container, false);

        messageId = messageDetailsView.findViewById(R.id.messageId);
        messageText = messageDetailsView.findViewById(R.id.messageText);

        messageId.setText(Long.toString(messageBundle.getLong("messageId")));
        messageText.setText(messageBundle.getString("messageText"));

        Button deleteMessage = messageDetailsView.findViewById(R.id.button_delete);

        if (!messageBundle.getBoolean("isTablet")) {

            deleteMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(parent, ChatWindow.class);
                    i.putExtra("delete", messageBundle.getLong("messageId"));
                    MessageDetails p = (MessageDetails) parent;
                    p.setResult(-1, i);
                    p.finish();
                }
            });
        } else {
            deleteMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatWindow p = (ChatWindow) parent;
                    p.deleteMessage(messageBundle.getLong("messageId"));
                    getView().setVisibility(View.INVISIBLE);
                }
            });
        }
        return messageDetailsView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        messageBundle = this.getArguments();
        isTablet = messageBundle.getBoolean("isTablet");
        parent = context;
    }
}
