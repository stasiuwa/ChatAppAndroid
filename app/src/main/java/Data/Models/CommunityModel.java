package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "communities")
public class CommunityModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "communityID")
    public long communityID;
    @NonNull
    @ColumnInfo(name = "Community Name")
    String communityName;
//    TODO dodać ikonke spolecznosci
//    TODO dodac liste uzytkownikow
//    TODO dodac uzytkownika-własciciela


    public CommunityModel(@NonNull String communityName) {
        this.communityName = communityName;
    }

    public long getCommunityID() {
        return communityID;
    }

    @NonNull
    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(@NonNull String communityName) {
        this.communityName = communityName;
    }
}
