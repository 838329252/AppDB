package com.example.appdb.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appdb.model.entity.Friend;

import java.util.List;

@Dao
public interface FriendDao {
    @Insert
    public long insertFriendToList(Friend friend);
    @Delete
    public int deleteFriendFromList(Friend friend);
    @Update
    public int updateFriendInfo(Friend friend);

    @Query("SELECT * FROM Friend WHERE fID=:id")
    public Friend getFriendInfo(String id);

    @Query("SELECT * FROM Friend WHERE uIP=:ip")
    public Friend getFriendInfoByIP(String ip);

    @Query("SELECT * FROM Friend")
    public List<Friend> getFriendList();

    @Query("SELECT fID FROM Friend")
    public List<String> getFriendDeviceIDList();



}
