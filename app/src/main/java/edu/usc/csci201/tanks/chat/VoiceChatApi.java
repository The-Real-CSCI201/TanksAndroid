package edu.usc.csci201.tanks.chat;

import retrofit.RestAdapter;

/**
 * Created by vmagro on 11/27/14.
 */
public interface VoiceChatApi {

    public static VoiceChatApi VoiceChatApi = new RestAdapter.Builder()
            .setEndpoint("http://voicechatapi.com/api/v1/")
            .build().create(VoiceChatApi.class);

    public Conference createConference();

}
