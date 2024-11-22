package Data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Data.Models.Channel;
import Data.Models.Community;

public class CommunityWithChannels  {
    @Embedded
    public Community community;

    @Relation(parentColumn = "communityID", entityColumn = "communityOwnerID")
    public List<Channel> channels;

}
