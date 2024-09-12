package Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long userID;
    @NonNull
    @ColumnInfo(name = "Username")
    String username;
    @NonNull
    @ColumnInfo(name = "Password")
    String password;
    @NonNull
    @ColumnInfo(name = "Email")
    String email;

    public UserModel(@NonNull String username, @NonNull String password, @NonNull String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }
}
