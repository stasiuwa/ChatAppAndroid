package Services;

import Data.DTO.UserDTO;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @GET("/api/users/me")
    Call<UserDTO> getCurrentUser(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @GET("/api/users")
    Call<UserDTO> getUser(
            @Header("Authorization") String token,
            @Field("userId") long userId);

    @POST("/api/users/description")
    Call<Void> updateUserDescription(
            @Field("descriptionDTO") String description
    );

    @FormUrlEncoded
    @POST("/api/users")
    Call<UserDTO> registerUser(
            @Header("Authorization") String token,
            @Field("username") String username
    );
}
