package com.example.appdb.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Friend.class,
        parentColumns = "fID",
        childColumns = "mFriendID",onDelete=CASCADE),indices = {@Index(value = "mFriendID")})
public class Message {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int mID;                              //信息ID
    @NonNull
    private String mFriendID;                    //好友设备ID
    private int mSendOrReceive;                  //发送或接收标志位，接收1，发送0
    private String mTime;                        //消息时间
    private int mMessageType;                    //消息类型，文本1，图片0
    private String mText;                        //文本内容
    private String mPicture;                     //原图路径
    private String mPictureThumbnail;            //缩略图路径
    private int mReadStatus;                     //已读或未读标志位，已读1，未读0
    private int mSendStatus;                     //发送成功或失败标志位，成功1，失败0

    public int getMID() {
        return mID;
    }

    public void setMID(int mID) {
        this.mID = mID;
    }


    public String getMFriendID() {
        return mFriendID;
    }

    public void setMFriendID(String mFriendID) {
        this.mFriendID = mFriendID;
    }

    public int getMSendOrReceive() {
        return mSendOrReceive;
    }

    public void setMSendOrReceive(int mSendOrReceive) {
        this.mSendOrReceive = mSendOrReceive;
    }

    public String getMTime() {
        return mTime;
    }

    public void setMTime(String mTime) {
        this.mTime = mTime;
    }

    public int getMMessageType() {
        return mMessageType;
    }

    public void setMMessageType(int mMessageType) {
        this.mMessageType = mMessageType;
    }

    public String getMText() {
        return mText;
    }

    public void setMText(String mText) {
        this.mText = mText;
    }

    public String getMPicture() {
        return mPicture;
    }

    public void setMPicture(String mPicture) {
        this.mPicture = mPicture;
    }

    public String getMPictureThumbnail() {
        return mPictureThumbnail;
    }

    public void setMPictureThumbnail(String mPictureThumbnail) {
        this.mPictureThumbnail = mPictureThumbnail;
    }

    public int getMReadStatus() {
        return mReadStatus;
    }

    public void setMReadStatus(int mReadStatus) {
        this.mReadStatus = mReadStatus;
    }

    public int getMSendStatus() {
        return mSendStatus;
    }

    public void setMSendStatus(int mSendStatus) {
        this.mSendStatus = mSendStatus;
    }
}
