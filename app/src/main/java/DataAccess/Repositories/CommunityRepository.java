package DataAccess.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Data.Models.Community;
import DataAccess.DAO.CommunityDAO;
import Data.Databases.CommunityDB;

public class CommunityRepository {
    private CommunityDAO dao;
    private LiveData<List<Community>> allCommunities;

    public CommunityRepository(Application application){
        CommunityDB mCommunityDB = CommunityDB.getDataBase(application);
        dao = mCommunityDB.communityDAO();
        allCommunities = dao.getAllCommunities();
    }
    /**
     * Mapping CommunityDTO to Community for Room Database
     * @param community object to mapping
     * @return new Community object
     */
    public Community mapCommunity(Community community){
        return new Community(
                community.getCommunityID(),
                community.getCommunityName(),
                community.getOwnerID(),
                community.getImageUrl(),
                community.getBasePermissions()
        );
    }
//    Communities
    public void addCommunity(Community community){
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.addCommunity(community);
        });
    }
    public LiveData<List<Community>> getCommunities() {
        return allCommunities;
    }
    public void deleteCommunity(Community community) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.deleteCommunity(community);
        });
    }
    public void updateCommunity(Community community){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> dao.updateCommunity(community));
    }
}
