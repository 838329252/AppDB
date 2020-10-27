package com.example.appdb.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appdb.model.entity.Message;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    public long insertOneMessageToList(Message message);

    @Delete
    public int deleteOneMessageFromList(Message message);

    @Delete
    public int deleteMessagesFromList(List<Message> messageList);

    @Update
    public int updateMessageInfo(Message message);

    @Update
    public int updateMessages(List<Message> messageList);

    @Query("SELECT * FROM Message WHERE mID=:id")
    public Message getMessageInfo(int id);

    @Query("SELECT * FROM (SELECT * FROM Message ORDER BY mID)A " +
            "GROUP BY mFriendID")
    public List<Message> getChatList();

    @Query("SELECT * FROM Message WHERE mFriendID=:id")
    public List<Message> getFriendMessages(String id);

    @Query("SELECT * FROM Message" +
            " WHERE mFriendID=:id " +
            "ORDER BY mID DESC " +
            "LIMIT :fromNum,:num")
    public List<Message> getFriendMessageList(String id, int num, int fromNum);

    @Query("SELECT * FROM Message " +
            "WHERE mFriendID=:id "+
            "ORDER BY mID DESC " +
            "LIMIT 1")
    public Message getFriendLatestMessageInfo(String id);

    @Query("SELECT COUNT(*) FROM Message" +
            " WHERE mFriendID=:id AND mReadStatus=0")
    public int getUnreadMessageNum(String id);

    @Query("SELECT * FROM Message" +
            " WHERE mFriendID=:id AND mReadStatus=0")
    public List<Message> getUnreadMessageList(String id);

    @Query("SELECT COUNT(*) FROM Message " +
            "WHERE mReadStatus=0")
    public int getTotalUnreadMessageNum();

}
