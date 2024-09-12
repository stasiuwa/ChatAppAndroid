package Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Data.Models.ChannelModel;

@Dao
public interface ChannelDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addChannel(ChannelModel channel);
    @Query("SELECT * FROM channels")
    LiveData<List<ChannelModel>> getAllChannels();
    @Delete
    void deleteChannel(ChannelModel channel);
}
