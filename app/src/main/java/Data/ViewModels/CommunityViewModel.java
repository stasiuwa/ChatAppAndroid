package Data.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Data.Models.CommunityModel;
import Data.Repositories.CommunityRepository;

public class CommunityViewModel extends AndroidViewModel {
    private final CommunityRepository communityRepository;
    private final LiveData<List<CommunityModel>> allCommunities;

    public CommunityViewModel(@NonNull Application application) {
        super(application);
        this.communityRepository = new CommunityRepository(application);
        this.allCommunities = communityRepository.getAllCommunities();
    }
    public void addCommunity(CommunityModel community) {
        communityRepository.addCommunity(community);
    }
    public LiveData<List<CommunityModel>> getAllCommunities() {
        return allCommunities;
    }
    public void deleteCommunity(CommunityModel community){
        communityRepository.deleteCommunity(community);
    }
}
