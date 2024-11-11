package Auth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface KeycloakService {

    @POST("/realms/szampchat/protocol/openid-connect/token")
    @FormUrlEncoded
    Call<Token> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String secret,
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("token")
    @FormUrlEncoded
    Call<Token> refreshAccessToken(
            @Field("client_id") String client_id,
            @Field("grant_type") String grant_type
    );

    @GET("/")
    Call<String> authorize();

    @POST("logout")
    @FormUrlEncoded
    void logout();
}