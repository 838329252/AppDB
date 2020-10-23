package com.example.appdb.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

@Entity
public class Friend {
    @NonNull
    @PrimaryKey
    private String fID;                           //好友设备ID
    @Embedded
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    private User fUser;                           //User对象
    private int fDeleteStatus;                    //是否被删除标志位,被删除1未被删除0

    public String getFID() {
        return fID;
    }

    public void setFID(String fID) {
        this.fID = fID;
    }

    public User getFUser() {
        return fUser;
    }

    public void setFUser(User fUser) {
        this.fUser = fUser;
    }

    public int getFDeleteStatus() {
        return fDeleteStatus;
    }

    public void setFDeleteStatus(int fDeleteStatus) {
        this.fDeleteStatus = fDeleteStatus;
    }
}
