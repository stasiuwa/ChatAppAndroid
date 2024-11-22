package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelDTO {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("communityId")
    private long communityId;
    @JsonProperty("type")
    private ChannelType type;
    @JsonProperty("participants")
    private List<String> participants;
    @JsonProperty("overwrites")
    private List<ChannelRoleDTO> overwrites;
    @JsonProperty("lastMessageId")
    private String lastMessageId;
}
