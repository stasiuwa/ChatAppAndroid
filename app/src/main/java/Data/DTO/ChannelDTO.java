package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("communityId")
    private String communityId;
    @JsonProperty("type")
    private ChannelType type;
    @JsonProperty("participants")
    private List<String> participants;
    @JsonProperty("overwrites")
    private List<ChannelRole> overwrites;
    @JsonProperty("lastMessageId")
    private String lastMessageId;
    @JsonProperty("messagesState")
    private ChannelMessagesState messagesState;
}
@Getter
@Setter
class ChannelRole {
    @JsonProperty("overwrites")
    private String overwrites;
    @JsonProperty("roleId")
    private String roleId;
}
enum ChannelMessagesState {
    @JsonProperty("NotFetched") NotFetched,
    @JsonProperty("PartlyFetched") PartlyFetched,
    @JsonProperty("FullyFetched") FullyFetched
}