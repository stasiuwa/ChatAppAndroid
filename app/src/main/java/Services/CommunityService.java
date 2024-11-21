package Services;

import java.util.List;

import Data.DTO.CommunityDTO;
import Data.DTO.FullCommunityDTO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface CommunityService {

    @GET("/api/communities")
    Call<List<CommunityDTO>> getCommunities(@Header("Authorization") String token);

    @Multipart
    @POST("/api/communities")
    Call<CommunityDTO> createCommunity(
            @Header("Authorization") String token,
//            @Part("file") MultipartBody.Part file, // narazie nie dziala, jebac te pliki
            @Part("community") RequestBody community // JSON
            );

    @POST("/api/communities/{communityId}/join")
    Call<Void> joinCommunity(
            @Header("Authorization") String token,
            @Body RequestBody requestBody,
            @Path("communityId") long communityId
    );

    @GET("/api/communities/{communityId}/info")
    Call<FullCommunityDTO> getFullCommunityInfo(@Header("Authorization") String token, @Path("communityId") long id);
}
