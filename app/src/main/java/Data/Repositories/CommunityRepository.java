package Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import Data.DAO.CommunityDAO;
import Data.Databases.CommunityDB;
import Data.Models.CommunityModel;

public class CommunityRepository {
    private CommunityDAO communityDAO;
    private LiveData<List<CommunityModel>> allCommunities;

    public CommunityRepository(Application application){
        CommunityDB mCommunityDB = CommunityDB.getDataBase(application);
        communityDAO = mCommunityDB.communityDAO();
        allCommunities = communityDAO.getAllCommunities();
    }
    public void addCommunity(CommunityModel community){
        CommunityDB.databaseWriteExecutor.execute(() -> {
            communityDAO.addCommunity(community);
        });
    }
    public LiveData<List<CommunityModel>> getAllCommunities() {
        return allCommunities;
    }
    public void deleteCommunity(CommunityModel community) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
            communityDAO.deleteCommunity(community);
        });
    }
}
