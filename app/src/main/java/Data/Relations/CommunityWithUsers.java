package Data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Data.Models.Community;
import Data.Models.User;

public class CommunityWithUsers {
    @Embedded
    public Community community;

    @Relation(parentColumn = "communityID", entityColumn = "communityOwnerID")
    public List<User> users;
}
