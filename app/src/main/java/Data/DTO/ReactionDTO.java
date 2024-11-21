package Data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.namespace.QName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionDTO {
    @JsonProperty("emoji")
    private String emoji;
    @JsonProperty("count")
    private int count;
    @JsonProperty("me")
    private boolean me;
}
