package Data.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("description")
    private String description;

    @JsonProperty("username")
    private String username;

    @JsonProperty("image_url")
    private String imageUrl;
}
