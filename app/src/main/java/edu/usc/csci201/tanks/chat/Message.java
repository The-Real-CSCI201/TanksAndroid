package edu.usc.csci201.tanks.chat;

/**
 * Created by vmagro on 11/30/14.
 */
public class Message {

    private String senderImageUrl;
    private String senderId;
    private String text;

    public Message(String senderImageUrl, String senderId, String text) {
        this.senderImageUrl = senderImageUrl;
        this.senderId = senderId;
        this.text = text;
    }

    public Message() {

    }

    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }
}
