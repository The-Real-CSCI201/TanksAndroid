package edu.usc.csci201.tanks.graphics;

/**
 * (C) 2014 nickentin
 * Created on 11/19/14.
 */
public interface ChatInterfaceListener {
    public void userDidSelectChannel(ChatChannel channel);

    public enum ChatChannel { ALL, TEAM, USER1, USER2, USER3 }
}
