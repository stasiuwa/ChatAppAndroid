package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(tableName = "communities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "communityID")
    public long communityID;
    @NonNull
    @ColumnInfo(name = "Community Name")
    public String communityName;

    @ColumnInfo(name = "Owner ID")
    public long ownerID;
    @ColumnInfo(name = "Image URL")
    public String imageUrl;
    @ColumnInfo(name = "Base Permissions")
    public int basePermissions;
}
