package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages")
public class MessageModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "messageID")
    public long messageID;
    @ColumnInfo(name = "chatID")
    public long chatID;
    @NonNull
    @ColumnInfo(name = "Text")
    public String text;
    @NonNull
    @ColumnInfo(name = "Sent Time", defaultValue = "(datetime('now'))")
    public String sentTime;
    @NonNull
    @ColumnInfo(name = "Sent by")
    public String username;

    public MessageModel(long chatID, @NonNull String text, @NonNull String sentTime, @NonNull String username) {
        this.chatID = chatID;
        this.text = text;
        this.sentTime = sentTime;
        this.username = username;
    }

    public long getMessageID() {
        return messageID;
    }

    public long getChatID() {
        return chatID;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(@NonNull String sentTime) {
        this.sentTime = sentTime;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }
}
