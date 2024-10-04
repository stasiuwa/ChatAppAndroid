package Data.Relations;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Data.Models.CommunityModel;
import Data.Models.RoleModel;

public class CommunityWithRoles {
    @Embedded
    public CommunityModel community;

    @Relation(parentColumn = "communityID", entityColumn = "roleCommunityID")
    public LiveData<List<RoleModel>> roles;
}
