package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chats"
//        ,foreignKeys = {
//        @ForeignKey(
//                entity = CommunityModel.class,
//                parentColumns = "communityID",
//                childColumns = "communityOwnerID",
//                onDelete = ForeignKey.CASCADE
//        )
//    }
)
public class Chat {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chatID")
    public long chatID;
    @NonNull
    @ColumnInfo(name = "Chat Name")
    public String chatName;
//    TODO dodac liste uzytkownikow

    @ColumnInfo(name = "communityOwnerID")
    public long communityID;

    public Chat(@NonNull String chatName, long communityID) {
        this.chatName = chatName;
        this.communityID = communityID;
    }

    public long getChatID() {
        return chatID;
    }

    @NonNull
    public String getChatName() {
        return chatName;
    }

    public void setChatName(@NonNull String chatName) {
        this.chatName = chatName;
    }

    public long getCommunityID() {
        return communityID;
    }

}
