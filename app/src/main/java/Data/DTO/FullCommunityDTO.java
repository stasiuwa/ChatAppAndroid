package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullCommunityDTO {
    @JsonProperty("community")
    CommunityDTO community;
    @JsonProperty("channels")
    List<ChannelDTO> channels;
    @JsonProperty("members")
    List<MemberDTO> members;
    @JsonProperty("roles")
    List<RoleDTO> roles;
}
