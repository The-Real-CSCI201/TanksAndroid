package edu.usc.csci201.tanks.chat;

import android.app.Activity;

import edu.usc.csci201.tanks.PlayerInfo;
import edu.usc.csci201.tanks.graphics.ChatInterfaceListener;

/**
 * Created by vmagro on 11/30/14.
 */
public class ChatListener implements ChatInterfaceListener {

    private Activity activity;

    public ChatListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void userDidSelectChannel(PlayerInfo user, ChatChannel channel) {
        switch (channel) {
            case ALL:
                activity.startActivity(ChatActivity.getIntentForChat(activity, ChatActivity.getAllChatName()));
                break;
            case TEAM:
                activity.startActivity(ChatActivity.getIntentForChat(activity, ChatActivity.getTeamChatName()));
                break;
            case USER:
                activity.startActivity(ChatActivity.getIntentForChat(activity, ChatActivity.getPlayerChatName(PlayerInfo.getMyId(), user.getId())));
                break;
        }
    }
}
