package Data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Data.Models.ChannelModel;
import Data.Models.CommunityModel;

public class CommunityWithChannels  {
    @Embedded
    public CommunityModel community;

    @Relation(parentColumn = "communityID", entityColumn = "communityOwnerID")
    public List<ChannelModel> channels;

}
