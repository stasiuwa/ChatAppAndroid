package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageAttachmentDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("path")
    private String path;
    @JsonProperty("size")
    private long size;
    @JsonProperty("name")
    private String name;
    @JsonProperty("messageId")
    private String messageId;
}
