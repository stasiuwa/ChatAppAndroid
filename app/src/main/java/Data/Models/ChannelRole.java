package Data.Models;

import androidx.room.ColumnInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChannelRole {
    @JsonProperty("id")
    private long roleId;
    @JsonProperty("overwrites")
    private long channelOverwrites;
}
