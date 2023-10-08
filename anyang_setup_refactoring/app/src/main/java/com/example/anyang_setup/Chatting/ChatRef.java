package com.example.anyang_setup.Chatting;

import java.util.ArrayList;
import java.util.List;

public class ChatRef {
    private String MaximumUser;
    private List<String> acceptUser;
    private String chatContext;

    public ChatRef() {}

    public ChatRef(String MaximumUser, String acceptUser, String chatContext) {
        this.acceptUser = new ArrayList<>();
        this.MaximumUser = MaximumUser;
        this.chatContext = chatContext;
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