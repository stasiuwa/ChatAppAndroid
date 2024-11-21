package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "communities")
public class CommunityModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "communityID")
    public long communityID;
    @NonNull
    @ColumnInfo(name = "Community Name")
    public String communityName;

    @ColumnInfo(name = "Owner ID")
    public long ownerID;
    @ColumnInfo(name = "Image URL")
    public UUID imageUrl;
    @ColumnInfo(name = "Base Permissions")
    public int basePermissions;
}
