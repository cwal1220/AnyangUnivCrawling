package com.example.anyang_setup.Chatting;

import java.util.ArrayList;
import java.util.List;

public class ChatRef {
    private String MaximumUser;
    private List<String> acceptUser;
    private String chatContext;
    private String chatOwner;

    public ChatRef() {}

    public ChatRef(String MaximumUser, String acceptUser, String chatContext, String chatOwner) {
        this.acceptUser = new ArrayList<>();
        this.MaximumUser = MaximumUser;
        this.chatContext = chatContext;
        this.chatOwner = chatOwner;
        this.acceptUser.add(acceptUser);
    }
    public String getMaximumUser() {
        return MaximumUser;
    }
    public List<String> getAcceptUser() {
        return this.acceptUser;
    }
    public String getChatContext(){
        return this.chatContext;
    }
    public void setAcceptUser(String acceptUser){
        this.acceptUser.add(acceptUser);
    }
}