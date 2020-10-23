package com.example.appdb.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appdb.model.entity.FriendRequest;

import java.util.List;


@Dao
public interface FriendRequestDao {
    @Insert
    public long insertFriendRequestToList(FriendRequest friendRequest);
    @Delete
    public int deleteFriendRequestFromList(FriendRequest friendRequest);
    @Update
    public int updateFriendRequestInfo(FriendRequest friendRequest);

    @Query("SELECT * FROM FriendRequest WHERE frID=:id")
    public FriendRequest getFriendRequestInfo(String id);

    @Query("SELECT * FROM FriendRequest ")
    public List<FriendRequest> getFriendRequestList();

    @Query("SELECT COUNT(*) FROM FriendRequest")
    public Integer getFriendRequestNum();

}
