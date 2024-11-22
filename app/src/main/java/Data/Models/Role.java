package Data.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "roles")
public class Role {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public long roleID;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "roleCommunityID")
    public long communityID;

    @ColumnInfo(name = "roleUserID")
    public long userID;
//    TODO dodac uprawnienia dla ról
//    Uprawnienia jako jakis wektor flag czy kilka poziomów ??


    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
