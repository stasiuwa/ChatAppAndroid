package Data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import Data.Models.RoleModel;
import Data.Models.UserModel;

public class UserWithRole {
    @Embedded
    UserModel user;

    @Relation(parentColumn = "userID", entityColumn = "roleUserID")
    public RoleModel role;
}
