package DataAccess.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Data.DTO.CommunityDTO;
import DataAccess.Repositories.CommunityRepository;
import Data.Models.Community;
import lombok.Getter;

/**
 * All communities are mapped from CommunityDTO to CommunityModel before any operation
 */
public class CommunityViewModel extends AndroidViewModel {
    private final CommunityRepository communityRepository;
    @Getter
    private final LiveData<List<Community>> allCommunities;

    public CommunityViewModel(@NonNull Application application) {
        super(application);
        this.communityRepository = new CommunityRepository(application);
        this.allCommunities = communityRepository.getCommunities();
    }

    public boolean communityExists(long id){
        return communityRepository.communityExists(id);
    }

    public void addCommunity(CommunityDTO community){
        communityRepository.addCommunity(communityRepository.mapCommunity(community));
    }
    public void updateCommunity(CommunityDTO community){
        communityRepository.updateCommunity(communityRepository.mapCommunity(community));
    }
    public void deleteCommunity(Community community){
        communityRepository.deleteCommunity(community);
    }
    public LiveData<Community> getCommunity(long id){
        return communityRepository.getCommunity(id);
    }
}
