package DataAccess.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Data.DTO.CommunityDTO;
import DataAccess.DAO.CommunityDAO;
import Data.Databases.CommunityDB;
import Data.Models.CommunityModel;

public class CommunityRepository {
    private CommunityDAO dao;
    private LiveData<List<CommunityModel>> allCommunities;

    public CommunityRepository(Application application){
        CommunityDB mCommunityDB = CommunityDB.getDataBase(application);
        dao = mCommunityDB.communityDAO();
        allCommunities = dao.getAllCommunities();
    }

    public CommunityModel mapCommunity(CommunityDTO communityDTO){
        return new CommunityModel(
                communityDTO.getId(),
                communityDTO.getName(),
                communityDTO.getOwnerId(),
                communityDTO.getImageUrl(),
                communityDTO.getBasePermissions()
        );
    }
//    Communities
    public void addCommunity(CommunityModel community){
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.addCommunity(community);
        });
    }
    public LiveData<List<CommunityModel>> getCommunities() {
        return allCommunities;
    }
    public void deleteCommunity(CommunityModel community) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.deleteCommunity(community);
        });
    }
    public void updateCommunity(CommunityModel community){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> dao.updateCommunity(community));
    }

    public LiveData<CommunityModel> getCommunity(long id){
        return dao.getCommunityById(id);
    }

}
