package DataAccess.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Data.DTO.ChannelDTO;
import Data.Models.Channel;
import DataAccess.Repositories.ChannelRepository;
import lombok.Getter;

public class ChannelViewModel extends AndroidViewModel {
    private final ChannelRepository channelRepository;
    @Getter
    private final LiveData<List<Channel>> allChannels;

    public ChannelViewModel(@NonNull Application application) {
        super(application);
        this.channelRepository = new ChannelRepository(application);
        this.allChannels = channelRepository.getChannels();
    }
    public void addChannel(ChannelDTO channelDTO){
        channelRepository.addChannel(channelRepository.mapChannel(channelDTO));
    }
    public void updateChannel(ChannelDTO channelDTO){
        channelRepository.addChannel(channelRepository.mapChannel(channelDTO));
    }
    public void deleteChannel(ChannelDTO channelDTO){
        channelRepository.deleteChannel(channelRepository.mapChannel(channelDTO));
    }
}
