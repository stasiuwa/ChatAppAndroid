package Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import Data.Models.CommunityModel;
import Data.Relations.CommunityWithChats;
import Data.Relations.CommunityWithChannels;

@Dao
public interface CommunityDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addCommunity(CommunityModel community);
    @Transaction
    @Query("SELECT * FROM communities")
    LiveData<List<CommunityModel>> getAllCommunities();
    @Delete
    void deleteCommunity(CommunityModel community);

    @Transaction
    @Query("SELECT * FROM communities")
    List<CommunityWithChats> getAllChats();

    @Transaction
    @Query("SELECT * FROM communities")
    List<CommunityWithChannels> getAllChannels();
}
