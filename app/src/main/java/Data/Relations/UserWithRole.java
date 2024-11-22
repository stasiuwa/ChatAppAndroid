package Data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import Data.Models.Role;
import Data.Models.User;

public class UserWithRole {
    @Embedded
    User user;

    @Relation(parentColumn = "userID", entityColumn = "roleUserID")
    public Role role;
}
