package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityDTO {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ownerId")
    private long ownerId;
    @JsonProperty("imageUrl")
    private UUID imageUrl;
    @JsonProperty("basePermissions")
    private int basePermissions;
}
