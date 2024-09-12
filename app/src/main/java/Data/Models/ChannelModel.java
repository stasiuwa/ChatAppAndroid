package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "channels"
//        ,foreignKeys = {
//        @ForeignKey(
//                entity = CommunityModel.class,
//                parentColumns = "communityID",
//                childColumns = "communityOwnerID",
//                onDelete = ForeignKey.CASCADE
//        )
//    }
)
public class ChannelModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "channelID")
    public long channelID;
    @NonNull
    @ColumnInfo(name = "Channel Name")
    String channelName;
//    TODO dodac liste uzytkownikow

    @ColumnInfo(name = "communityOwnerID")
    public long communityID;

    public ChannelModel(@NonNull String channelName) {
        this.channelName = channelName;
    }

    public long getChannelID() {
        return channelID;
    }

    public void setChannelID(long channelID) {
        this.channelID = channelID;
    }

    @NonNull
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(@NonNull String channelName) {
        this.channelName = channelName;
    }
}
