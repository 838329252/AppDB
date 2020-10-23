package com.example.appdb.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @NonNull
    @PrimaryKey
    private String uNickName;                //用户昵称
    private String uHeadPortrait;            //用户头像路径
    private int uSex;                        //用户性别，女0男1
    private String uIP;                      //用户IP
    private int uStatus;                     //用户在线或离线状态，在线1离线0
    private String uRemark;                  //用户备注

    public String getUNickName() {
        return uNickName;
    }

    public void setUNickName(String uNickName) {
        this.uNickName = uNickName;
    }

    public String getUHeadPortrait() {
        return uHeadPortrait;
    }

    public void setUHeadPortrait(String uHeadPortrait) {
        this.uHeadPortrait = uHeadPortrait;
    }

    public int getUSex() {
        return uSex;
    }

    public void setUSex(int uSex) {
        this.uSex = uSex;
    }

    public String getUIP() {
        return uIP;
    }

    public void setUIP(String uIP) {
        this.uIP = uIP;
    }

    public int getUStatus() {
        return uStatus;
    }

    public void setUStatus(int uStatus) {
        this.uStatus = uStatus;
    }

    public String getURemark() {
        return uRemark;
    }

    public void setURemark(String uRemark) {
        this.uRemark = uRemark;
    }
}
