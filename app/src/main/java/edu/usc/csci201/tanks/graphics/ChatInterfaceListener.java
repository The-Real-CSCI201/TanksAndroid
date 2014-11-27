package edu.usc.csci201.tanks.graphics;

import android.graphics.Bitmap;

/**
 * (C) 2014 nickentin
 * Created on 11/19/14.
 */
public interface ChatInterfaceListener {
    public void userDidSelectChannel(ChatChannel channel);

    public Bitmap getImageForUser(String userid);

    public enum ChatChannel {ALL, TEAM, USER1, USER2, USER3}
}
