package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "channels")
public class ChannelModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long channelID;
    @NonNull
    @ColumnInfo(name = "Channel Name")
    String channelName;
//    TODO dodac liste uzytkownikow

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
