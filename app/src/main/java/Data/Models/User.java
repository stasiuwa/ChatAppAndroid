package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    private long userId;
    @ColumnInfo(name = "Username")
    private String username;
    @ColumnInfo(name = "Image URL")
    private String imageUrl;
    @ColumnInfo(name = "Description")
    private String description;
}
