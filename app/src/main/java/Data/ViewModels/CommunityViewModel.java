package Data.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Data.Models.ChannelModel;
import Data.Models.CommunityModel;
import Data.Relations.CommunityWithChannels;
import Data.Relations.CommunityWithChats;
import Data.Repositories.CommunityRepository;

public class CommunityViewModel extends AndroidViewModel {
    private final CommunityRepository communityRepository;
    private final LiveData<List<CommunityModel>> allCommunities;

    public CommunityViewModel(@NonNull Application application) {
        super(application);
        this.communityRepository = new CommunityRepository(application);
        this.allCommunities = communityRepository.getCommunities();
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
    public LiveData<List<CommunityWithChannels>> getChannels(long id){
        return communityRepository.getChannels(id);
    }
    public LiveData<List<CommunityWithChats>> getAllChats(){
        return communityRepository.getAllChats();
    }

    public LiveData<List<CommunityWithChats>> getChats(long id){
        return communityRepository.getChats(id);
    }
}
