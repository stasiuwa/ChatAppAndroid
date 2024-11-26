package Data.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID")
    @JsonProperty("id")
    private long userId;
    @ColumnInfo(name = "Username")
    @JsonProperty("username")
    private String username;
    @ColumnInfo(name = "Image URL")
    @JsonProperty("imageUrl")
    private String imageUrl;
    @ColumnInfo(name = "Description")
    @JsonProperty("description")
    private String description;
}
