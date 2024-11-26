package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import Data.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    @JsonProperty("roles")
    private List<String> roles;
    @JsonProperty("user")
    private User user;
}
