package com.lab8.chat;

import java.util.List;

public class ChatMessage {

    private MessageType type;
    private String sender;
    private String content;
    private List<String> onlineUsers;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
