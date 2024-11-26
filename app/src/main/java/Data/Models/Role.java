package Data.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "roles")
public class Role {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Role ID")
    @JsonProperty("id")
    public long roleId;
    @ColumnInfo(name = "Role Name")
    @JsonProperty("name")
    private String name;
    @ColumnInfo(name = "Community ID")
    @JsonProperty("communityId")
    private long communityId;
    @ColumnInfo(name = "Permission Overwrites")
    @JsonProperty("permissionOverwrites")
    private long permissionOverwrites;
}
