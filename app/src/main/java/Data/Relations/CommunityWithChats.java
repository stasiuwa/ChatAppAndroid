package Data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Data.Models.Chat;
import Data.Models.Community;

public class CommunityWithChats {
    @Embedded
    public Community community;

    @Relation(parentColumn = "communityID", entityColumn = "communityOwnerID")
    public List<Chat> chats;

}
