package Data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Data.Models.ChatModel;
import Data.Models.CommunityModel;

public class CommunityWithChats {
    @Embedded
    public CommunityModel community;

    @Relation(parentColumn = "communityID", entityColumn = "communityOwnerID")
    public List<ChatModel> chats;

}
