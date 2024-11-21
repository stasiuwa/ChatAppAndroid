package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
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
    private MessageDTO respondObject;
    @JsonProperty("reactions")
    private List<ReactionDTO> reactions;
    @JsonProperty("attachments")
    private List<MessageAttachmentDTO> attachments;
    @JsonProperty("gifLink")
    private String gifLink;
}
