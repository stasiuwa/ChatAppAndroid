package Data.Models;

import androidx.room.ColumnInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChannelRole {
    @ColumnInfo(name = "Role Overwrites")
    private long channelOverwrites;
    @ColumnInfo(name = "Role Id")
    private long roleId;
}
