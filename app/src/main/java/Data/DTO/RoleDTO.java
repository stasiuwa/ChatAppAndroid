package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("communityId")
    private String communityId;
    @JsonProperty("permissionOverwrites")
    private String permissionOverwrites;
}
