package Data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Data.Models.CommunityModel;
import Data.Models.UserModel;

public class CommunityWithUsers {
    @Embedded
    public CommunityModel community;

    @Relation(parentColumn = "communityID", entityColumn = "communityOwnerID")
    public List<UserModel> users;
}
