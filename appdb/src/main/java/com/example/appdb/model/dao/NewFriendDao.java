package com.example.appdb.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appdb.model.entity.NewFriend;

@Dao
public interface NewFriendDao {
    @Insert
    public long insertNewFriendToList(NewFriend newFriend);
    @Delete
    public int deleteNewFriendFromList(NewFriend newFriend);
    @Update
    public int updateNewFriendInfo(NewFriend newFriend);

    @Query("SELECT * FROM NewFriend WHERE nfID=:id")
    public NewFriend getNewFriendInfo(String id);
}
