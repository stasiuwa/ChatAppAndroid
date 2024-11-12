package Auth;

import Data.DTO.UserInfo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserService {

    @GET("/api/users/me")
    Call<UserInfo> getCurrentUser(@Header("Authorization") String token);

    @GET("/api/users")
    Call<UserInfo> getUser(@Header("Authorization") String token, @Field("userId") long userId);
}
