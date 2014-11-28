package edu.usc.csci201.tanks.graphics;

import android.content.Context;
import android.graphics.Bitmap;

import edu.usc.csci201.tanks.gameplay.Game;
import edu.usc.csci201.tanks.gameplay.Player;

/**
 * (C) 2014 nickentin
 * Created on 11/19/14.
 */
public interface ChatInterfaceListener {

    public void init(Context context, Game game);

    public void userDidSelectChannel(ChatChannel channel);

    public void userDidSelectPlayer(Player player);

    public Bitmap getImageForPlayer(Context context, Player player);

    public static enum ChatChannel {ALL, TEAM}

}
