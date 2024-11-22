package DataAccess.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import Data.Models.Community;

@Dao
public interface CommunityDAO {
    @Insert
    void addCommunity(Community community);

    @Update
    void updateCommunity(Community community);

    @Transaction
    @Query("SELECT * FROM communities")
    LiveData<List<Community>> getAllCommunities();

    @Transaction
    @Query("SELECT * FROM communities WHERE communityID = :id")
    LiveData<Community> getCommunityById(long id);

    @Delete
    void deleteCommunity(Community community);

    @Query("SELECT COUNT(*) FROM communities WHERE communityID = :id")
    int communityExists(long id);
}
