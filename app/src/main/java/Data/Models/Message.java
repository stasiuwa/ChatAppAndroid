package Data.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "messages")
public class Message {
    @PrimaryKey(autoGenerate = true)
    @JsonProperty("id")
    private String id;
    @JsonProperty("text")
    private String text;
    @JsonProperty("channelId")
    private String channelId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("edited")
    private boolean edited;
    @JsonProperty("updatedAt")
    private Date updatedAt;
    @JsonProperty("dateFormatted")
    private String dateFormatted;
    @JsonProperty("respondsToMessage")
    private String respondsToMessage;
    @JsonProperty("respondObject")
    private Message respondObject;
    @JsonProperty("reactions")
    private List<MessageReaction> reactions;
    @JsonProperty("attachments")
    private List<MessageAttachment> attachments;
    @JsonProperty("gifLink")
    private String gifLink;
}
