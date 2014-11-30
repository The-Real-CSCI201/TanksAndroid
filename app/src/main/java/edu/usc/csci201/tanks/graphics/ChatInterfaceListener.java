package edu.usc.csci201.tanks.graphics;

import android.graphics.Bitmap;

import edu.usc.csci201.tanks.PlayerInfo;

/**
 * (C) 2014 nickentin
 * Created on 11/19/14.
 */
public interface ChatInterfaceListener {
    // NOTE: user should not be used unless channel==USER
    public void userDidSelectChannel(ChatChannel channel, PlayerInfo user);

    public enum ChatChannel {ALL, TEAM, USER}
}
