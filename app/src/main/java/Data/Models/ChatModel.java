package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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
public class ChatModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chatID")
    public long chatID;
    @NonNull
    @ColumnInfo(name = "Chat Name")
    String chatName;
//    TODO dodac liste uzytkownikow

    @ColumnInfo(name = "communityOwnerID")
    public long communityID;

    public ChatModel(@NonNull String chatName) {
        this.chatName = chatName;
    }

    public long getChatID() {
        return chatID;
    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }

    @NonNull
    public String getChatName() {
        return chatName;
    }

    public void setChatName(@NonNull String chatName) {
        this.chatName = chatName;
    }
}
