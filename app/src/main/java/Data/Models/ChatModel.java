package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chats")
public class ChatModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long chatID;
    @NonNull
    @ColumnInfo(name = "Chat Name")
    String chatName;
//    TODO dodac liste uzytkownikow

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
