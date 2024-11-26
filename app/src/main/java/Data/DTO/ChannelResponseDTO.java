package Data.DTO;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelResponseDTO {
    @JsonProperty("channel")
    private ChannelDTO channel;
    @Nullable
    @JsonProperty("participants")
    private List<String> participants;
    @JsonProperty("overwrites")
    private List<ChannelRoleDTO> overwrites;
}
