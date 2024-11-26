package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelRoleDTO {
    @JsonProperty("overwrites")
    private long overwrites;
    @JsonProperty("roleId")
    private long roleId;
}
