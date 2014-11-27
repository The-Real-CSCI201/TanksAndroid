package edu.usc.csci201.tanks.chat;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.POST;

/**
 * Created by vmagro on 11/27/14.
 */
public interface VoiceChatApi {

    public static VoiceChatApi VoiceChatApi = new RestAdapter.Builder()
            .setEndpoint("http://voicechatapi.com/api/v1")
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build().create(VoiceChatApi.class);

    @POST("/conference/")
    public void createConference(Callback<Conference> callback);

}
