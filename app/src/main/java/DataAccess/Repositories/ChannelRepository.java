package DataAccess.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import Data.DTO.ChannelDTO;
import Data.DTO.ChannelRoleDTO;
import Data.DTO.ChannelType;
import Data.Databases.CommunityDB;
import Data.Models.Channel;
import Data.Models.ChannelRole;
import DataAccess.DAO.ChannelDAO;

public class ChannelRepository {
    private ChannelDAO dao;
    private LiveData<List<Channel>> allChannels;

    public ChannelRepository(Application application) {
        CommunityDB communityDB = CommunityDB.getDataBase(application);
        dao = communityDB.channelDAO();
        allChannels = dao.getAllChannels();
    }

    /**
     * Mapping ChannelDTO to Channel for Room Database
     * @param channelDTO object to mapping
     * @return new Channel object
     */
    public Channel mapChannel(ChannelDTO channelDTO){
        List<ChannelRole> overwrites = new ArrayList<>();
        for(ChannelRoleDTO role : channelDTO.getOverwrites()){
            overwrites.add(new ChannelRole(role.getChannelOverwrites(), role.getRoleId()));
        }
        return new Channel(
                channelDTO.getId(),
                channelDTO.getName(),
                channelDTO.getCommunityId(),
//                ChannelType from server is 0-1
                (channelDTO.getType()==1) ? ChannelType.VOICE_CHANNEL : ChannelType.TEXT_CHANNEL,
                channelDTO.getParticipants(),
                overwrites
        );
    }
    public void addChannel(Channel channel){
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.addChannel(channel);
        });
    }
    public LiveData<List<Channel>> getChannels() {return allChannels;}
    public void deleteChannel(Channel channel){
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.deleteChannel(channel);
        });
    }
    public void updateChannel(Channel channel){
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.updateChannel(channel);
        });
    }
}
