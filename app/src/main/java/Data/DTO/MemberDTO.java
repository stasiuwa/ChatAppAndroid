package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("roles")
    private List<String> roles;
    @JsonProperty("user")
    private UserDTO user;
    @JsonProperty("communityId")
    private String communityId;
}
