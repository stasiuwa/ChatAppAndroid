package DataAccess.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import Data.DTO.ChannelDTO;
import Data.DTO.ChannelRoleDTO;
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
                channelDTO.getType(),
                channelDTO.getParticipants(),
                overwrites
        );
    }
//    TODO dokonczyc
    public void addChannel(Channel channel){

    }
}
