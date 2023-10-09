package com.example.anyang_setup.Chatting.SubActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anyang_setup.Chatting.ChatActivity;
import com.example.anyang_setup.R;

public class ChatRoomInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView chatContextEditText;
    private TextView maxChatEditText;
    private TextView chatNameEditText;
    private Button joinButton;

    private String userName;
    private String chatName;
    private String chatNowUser;
    private String maximumChatUser;
    private String chatContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_info);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        chatName = intent.getStringExtra("chatRoom");
        maximumChatUser = intent.getStringExtra("userinfo");
        chatContext = intent.getStringExtra("chatContext");
        chatNowUser = intent.getStringExtra("chatNowUser");

        chatContextEditText = findViewById(R.id.chatContextEditText);
        maxChatEditText = findViewById(R.id.maxChatEditText);
        chatNameEditText = findViewById(R.id.chatNameEditText);
        joinButton = findViewById(R.id.joinButton);

        joinButton.setOnClickListener(this);

        chatContextEditText.setText(chatContext);
        chatNameEditText.setText(chatName);
        maxChatEditText.setText(chatNowUser + "/" + maximumChatUser);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.joinButton :
            {
                Intent intent = new Intent(ChatRoomInfoActivity.this, ChatRoomActivity.class);
                intent.putExtra("userinfo", userName);
                intent.putExtra("chatRoom", chatName);
                startActivity(intent);
                finish();
                break;
            }
        }
    }
}