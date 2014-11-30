package edu.usc.csci201.tanks.graphics;

import android.graphics.Bitmap;

/**
 * (C) 2014 nickentin
 * Created on 11/19/14.
 */
public interface ChatInterfaceListener {
    // NOTE: user should not be used unless channel==USER
    public void userDidSelectChannel(ChatChannel channel, String user);

    public Bitmap getImageForUser(String userid);

    public enum ChatChannel { ALL, TEAM, USER }
}
