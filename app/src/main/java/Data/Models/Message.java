package Data.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(tableName = "messages")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @PrimaryKey(autoGenerate = true)
    @JsonProperty("id")
    public long id;
    @JsonProperty("text")
    public String text;
    @JsonProperty("channelId")
    public long channelId;
    @JsonProperty("userId")
    public long userId;
    @JsonProperty("edited")
    public boolean edited;
    @JsonProperty("updatedAt")
    @TypeConverters(Data.Models.TypeConverters.class)
    public Date updatedAt;
    @JsonProperty("dateFormatted")
    public String dateFormatted;
    @JsonProperty("respondsToMessage")
    public String respondsToMessage;
    @JsonProperty("respondObject")
    @TypeConverters(Data.Models.TypeConverters.class)
    public Message respondObject;
    @JsonProperty("reactions")
    @TypeConverters(Data.Models.TypeConverters.class)
    public List<MessageReaction> reactions;
    @JsonProperty("attachments")
    @TypeConverters(Data.Models.TypeConverters.class)
    public List<MessageAttachment> attachments;
    @JsonProperty("gifLink")
    public String gifLink;
}
