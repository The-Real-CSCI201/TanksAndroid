package edu.usc.csci201.tanks.graphics;

import edu.usc.csci201.tanks.PlayerInfo;

/**
 * (C) 2014 nickentin
 * Created on 11/24/14.
 */
public class DebugChatListener implements ChatInterfaceListener {
    @Override
    public void userDidSelectChannel(ChatChannel channel, PlayerInfo user) {
        switch (channel) {
            case ALL:
                System.out.println("Switched to chat channel: ALL");
                break;
            case TEAM:
                System.out.println("Switched to chat channel: TEAM");
                break;
            case USER:
                System.out.println("Switched to chat channel: USER (" + user.getName() + ")");
                break;
        }
    }

}
