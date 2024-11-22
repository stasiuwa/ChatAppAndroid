package Data.Relations;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Data.Models.Community;
import Data.Models.Role;

public class CommunityWithRoles {
    @Embedded
    public Community community;

    @Relation(parentColumn = "communityID", entityColumn = "roleCommunityID")
    public LiveData<List<Role>> roles;
}
