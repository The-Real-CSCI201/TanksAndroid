package edu.usc.csci201.tanks.network;

import java.util.List;

import edu.usc.csci201.tanks.network.responses.Game;
import edu.usc.csci201.tanks.network.responses.JoinResponse;
import edu.usc.csci201.tanks.network.responses.UserResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by vmagro on 11/22/14.
 */
public interface TanksApi {

    public static TanksApi TanksApi = new RestAdapter.Builder()
            .setEndpoint("http://192.168.0.205:1337")
            .build().create(TanksApi.class);

    @FormUrlEncoded
    @POST("/player")
    public UserResponse registerUser(@Field("name") String name, @Field("gcmId") String gcmId);

    @FormUrlEncoded
    @PUT("/player/{id}")
    public UserResponse updateUserGcmId(@Path("id") String userId, @Field("gcmId") String gcmId);

    @FormUrlEncoded
    @POST("/game/join/{id}")
    public JoinResponse joinGame(@Path("id") String gameId, @Field("playerId") String playerId);

    @GET("/game")
    public List<Game> listGames();

    @GET("/game")
    public void listGames(Callback<List<Game>> callback);

}
