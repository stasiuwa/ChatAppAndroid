package Data.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Data.Models.CommunityModel;
import Data.Repositories.CommunityRepository;

public class CommunityViewModel extends AndroidViewModel {
    private final CommunityRepository mCommunityRepository;
    private final LiveData<List<CommunityModel>> mAllCommunities;

    public CommunityViewModel(@NonNull Application application) {
        super(application);
        this.mCommunityRepository = new CommunityRepository(application);
        this.mAllCommunities = mCommunityRepository.getAllCommunities();
    }
    public void addCommunity(CommunityModel community) {
        mCommunityRepository.addCommunity(community);
    }
    public LiveData<List<CommunityModel>> getAllCommunities() {
        return mAllCommunities;
    }
    public void deleteCommunity(CommunityModel community){
        mCommunityRepository.deleteCommunity(community);
    }
}
