package com.example.appdb.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

@Entity
public class NewFriend {
    @NonNull
    @PrimaryKey
    private String nfID;                       //设备ID
    @Embedded
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    private User nfUser;                    //User对象

    public String getNfID() {
        return nfID;
    }

    public void setNfID(String nfID) {
        this.nfID = nfID;
    }

    public User getNfUser() {
        return nfUser;
    }

    public void setNfUser(User nfUser) {
        this.nfUser = nfUser;
    }
}
