package Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Data.Models.CommunityModel;

@Dao
public interface CommunityDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addCommunity(CommunityModel community);
    @Query("SELECT * FROM communities")
    LiveData<List<CommunityModel>> getAllCommunities();
    @Delete
    void deleteCommunity(CommunityModel community);
}
