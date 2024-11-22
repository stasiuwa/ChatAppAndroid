package Services;

import Data.DTO.ChannelDTO;
import Data.DTO.ChannelType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ChannelService {

    @POST("/api/communities/{communityId}/channels")
    Call<ChannelDTO> createChannel(
            @Header("Authorization") String token,
            @Path("communityId") long communityId,
            @Body RequestBody requestBody
            );

    @PUT("/api/channels/{channelId}")
    Call<ChannelDTO> editChannel(
            @Header("Authorization") String token,
            @Path("channelId") long channelId
    );

    @DELETE("/api/channels/{channelId}")
    Call<Void> deleteChannel(
            @Header("Authorization") String token,
            @Path("channelsId") long channelId
    );
}
