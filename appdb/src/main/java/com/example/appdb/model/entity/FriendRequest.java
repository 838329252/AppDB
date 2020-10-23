package com.example.appdb.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

@Entity
public class FriendRequest {
    @NonNull
    @PrimaryKey
    private String frID;                          //设备ID
    @Embedded
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    private User frUser;                       //User对象
    private String frRequestContent;           //申请内容

    public String getFrID() {
        return frID;
    }

    public void setFrID(String frID) {
        this.frID = frID;
    }

    public User getFrUser() {
        return frUser;
    }

    public void setFrUser(User frUser) {
        this.frUser = frUser;
    }

    public String getFrRequestContent() {
        return frRequestContent;
    }

    public void setFrRequestContent(String frRequestContent) {
        this.frRequestContent = frRequestContent;
    }
}
