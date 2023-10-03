package com.example.anyang_setup;

public class Chat_Message {
    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT = "bot";

    private String message;
    private String sentBy;

    public Chat_Message(String message, String sentBy) {
        this.message = message;
        this.sentBy = sentBy;
    }

    public Chat_Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}