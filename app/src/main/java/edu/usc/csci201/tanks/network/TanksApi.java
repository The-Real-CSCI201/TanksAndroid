package edu.usc.csci201.tanks.network;

import java.util.List;

import edu.usc.csci201.tanks.Move;
import edu.usc.csci201.tanks.network.responses.Game;
import edu.usc.csci201.tanks.network.responses.JoinResponse;
import edu.usc.csci201.tanks.network.responses.MoveResponse;
import edu.usc.csci201.tanks.network.responses.UserResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;
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
            .setEndpoint("https://mjbpeiiwvt.localtunnel.me/")
//            .setEndpoint("http://192.168.0.205:1337")
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

    @FormUrlEncoded
    @POST("/game/join/{id}")
    public void joinGame(@Path("id") String gameId, @Field("playerId") String playerId, Callback<JoinResponse> callback);

    @FormUrlEncoded
    @POST("/game")
    public Game createGame(@Field("name") String name);

    @FormUrlEncoded
    @POST("/game")
    public void createGame(@Field("name") String name, Callback<Game> callback);

    @GET("/game")
    public List<Game> listGames();

    @GET("/game")
    public void listGames(Callback<List<Game>> callback);

    @GET("/game/{id}")
    public Game getGame(@Path("id") String id);

    @GET("/game/{id}")
    public void getGame(@Path("id") String id, Callback<Game> callback);

    @POST("/game/move/{id}")
    public MoveResponse move(@Path("id") String gameId, @Body Move move);

    @POST("/game/move/{id}")
    public void move(@Path("id") String gameId, @Body Move move, Callback<MoveResponse> callback);

}
