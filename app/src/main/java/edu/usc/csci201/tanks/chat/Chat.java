package edu.usc.csci201.tanks.chat;

import edu.usc.csci201.tanks.PlayerInfo;
import edu.usc.csci201.tanks.graphics.ChatInterfaceListener;

/**
 * Created by vmagro on 11/30/14.
 */
public class Chat implements ChatInterfaceListener {

    private static Chat instance;

    public static Chat getInstance() {
        if (instance == null)
            instance = new Chat();
        return instance;
    }

    private Chat() {

    }

    @Override
    public void userDidSelectChannel(ChatChannel channel, PlayerInfo user) {

    }
}
