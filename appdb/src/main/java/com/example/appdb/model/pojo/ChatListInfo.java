package com.example.appdb.model.pojo;

import com.example.appdb.model.entity.Message;
import com.example.appdb.model.entity.User;

public class ChatListInfo {
    private Message message;
    private User user;
    private int unreadNum;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }
}
