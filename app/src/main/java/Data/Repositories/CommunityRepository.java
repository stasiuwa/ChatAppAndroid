package Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import Data.DAO.CommunityDAO;
import Data.Databases.CommunityDB;
import Data.Models.CommunityModel;

public class CommunityRepository {
    private CommunityDAO mCommunityDAO;
    private LiveData<List<CommunityModel>> mAllCommunities;

    public CommunityRepository(Application application){
        CommunityDB mCommunityDB = CommunityDB.getDataBase(application);
        mCommunityDAO = mCommunityDB.communityDAO();
        mAllCommunities = mCommunityDAO.getAllCommunities();
    }
    public void addCommunity(CommunityModel community){
        CommunityDB.databaseWriteExecutor.execute(() -> {
            mCommunityDAO.addCommunity(community);
        });
    }
    public LiveData<List<CommunityModel>> getAllCommunities() {
        return mAllCommunities;
    }
    public void deleteCommunity(CommunityModel community) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
            mCommunityDAO.deleteCommunity(community);
        });
    }
}
