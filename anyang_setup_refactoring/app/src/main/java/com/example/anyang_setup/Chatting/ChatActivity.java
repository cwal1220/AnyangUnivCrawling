package com.example.anyang_setup.Chatting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anyang_setup.Chatting.SubActivity.ChatRoomActivity;
import com.example.anyang_setup.Chatting.SubActivity.CreateNewChatActivity;
import com.example.anyang_setup.HomeActivity;
import com.example.anyang_setup.Info.UserInfoActivity;
import com.example.anyang_setup.MainActivity;
import com.example.anyang_setup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView chat_list;
    private String userName;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button addChatRoomButton;
    private TextView chattingStatusLabel;
    private ArrayAdapter<String> adapter;

    ChildEventListener defaultEventListener;
    ChildEventListener myChatEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseApp.initializeApp(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        chat_list = (ListView) findViewById(R.id.chat_list);
        chattingStatusLabel = findViewById(R.id.chattingStatusLabel);
        addChatRoomButton = findViewById(R.id.addChatRoomButton);

        addChatRoomButton.setOnClickListener(this);
        chattingStatusLabel.setOnClickListener(this);
        userName = getIntent().getStringExtra("name");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chat_list.setAdapter(adapter);

        chat_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView parent, View v, int position, long id){
                EditText edittext = new EditText(parent.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                String selected_item = (String)parent.getItemAtPosition(position);
                final int[] workingFlag = {0};
                boolean deleteFlag = false;

                builder.setTitle("채팅방 삭제");
                builder.setMessage("*주의* 채팅방을 삭제할 경우 복구할 수 없습니다.");
//                builder.setView(edittext);
                builder.setPositiveButton("삭제",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                databaseReference.child("chat").child(selected_item).addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);

                                        assert chatDTO != null;
                                        if(workingFlag[0] == 0)
                                        {
                                            if(Objects.equals(chatDTO.getUserName(), "Notice") && chatDTO.getMessage().contains(userName))
                                            {
                                                Log.e("Telechips", chatDTO.getMessage()+ " " + chatDTO.getUserName());
                                                databaseReference.child("chat").child(selected_item).removeValue();

                                                Toast.makeText(getApplicationContext(), "채팅방이 정상 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), "자신이 만든 채팅방 외에는 삭제할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                            }

                                            workingFlag[0]++;
                                        }
                                    }
                                    @Override
                                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                    }

                                    @Override
                                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                                    }

                                    @Override
                                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();

                return true;
            }
        });

        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selected_item = (String)adapterView.getItemAtPosition(i);

                databaseReference.child("chat").child(selected_item).child("chatRef").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            if (dataSnapshot.exists()) {

                                List<String> acceptUser = new ArrayList<>();
                                boolean joinFlag = false;


                                for (DataSnapshot tmp : dataSnapshot.child("acceptUser").getChildren()) {
                                    acceptUser.add(tmp.getValue(String.class));
                                }

                                String maximumUser = dataSnapshot.child("maximumUser").getValue(String.class);


                                for(int j = 0 ; j < acceptUser.size(); j++)
                                {
                                    Log.e("Telechips", acceptUser.get(j));
                                }

                                Log.e("Telechips", maximumUser + " / " + acceptUser.size());


                                if(acceptUser.contains(userName))
                                {
                                    joinFlag = true;
                                }

                                if(Integer.parseInt(maximumUser) > acceptUser.size())
                                {
                                    joinFlag = true;
                                }

                                if(joinFlag)
                                {
                                    Map<String, Object> updateData = new HashMap<>();
                                    acceptUser.add(userName);
                                    updateData.put("acceptUser", acceptUser);

                                    databaseReference.child("chat").child(selected_item).child("chatRef").updateChildren(updateData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Intent intent = new Intent(ChatActivity.this, ChatRoomActivity.class);
                                                    intent.putExtra("userinfo", userName);
                                                    intent.putExtra("chatRoom", selected_item);
                                                    startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), "채팅방 입장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "정원이 초과하여 입장할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });

        setEventListener();
        showChatList();
    }

    public void setEventListener()
    {
        defaultEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.add(snapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                adapter.remove(snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myChatEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DataSnapshot firstDataSnapshot = null;
                for (DataSnapshot test : snapshot.getChildren()) {
                    firstDataSnapshot = test;
                    break;
                }

                ChatDTO chatDTO = firstDataSnapshot.getValue(ChatDTO.class);

                if(chatDTO.getMessage().contains(userName))
                {
                    adapter.add(snapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                adapter.remove(snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.addChatRoomButton :
            {
                Intent intent = new Intent(ChatActivity.this, CreateNewChatActivity.class);
                intent.putExtra("userinfo", userName);
                startActivity(intent);

                break;
            }
            case R.id.chattingStatusLabel :
            {
                String nowStatus = chattingStatusLabel.getText().toString();

                Log.e("Telechips", nowStatus);

                if(nowStatus.contains("현재 개설된 채팅방"))
                {
                    chattingStatusLabel.setText("내가 개설한 채팅방");
                    adapter.clear();

                    databaseReference.child("chat").removeEventListener(defaultEventListener);
                    databaseReference.child("chat").addChildEventListener(myChatEventListener);
                }
                else if(nowStatus.contains("내가 개설한 채팅방"))
                {
                    chattingStatusLabel.setText("현재 개설된 채팅방");
                    adapter.clear();
                    databaseReference.child("chat").removeEventListener(myChatEventListener);
                    databaseReference.child("chat").addChildEventListener(defaultEventListener);
                }

                break;
            }
        }
    }
    private void showChatList() {
        // 리스트 어댑터 생성 및 세팅
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").addChildEventListener(defaultEventListener);
    }


}