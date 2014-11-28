package edu.usc.csci201.tanks.graphics;

import android.content.Context;
import android.graphics.Bitmap;

import edu.usc.csci201.tanks.gameplay.Game;
import edu.usc.csci201.tanks.gameplay.Player;

/**
 * (C) 2014 nickentin
 * Created on 11/24/14.
 */
public class DebugChatListener implements ChatInterfaceListener {
    @Override
    public void init(Context context, Game game) {

    }

    @Override
    public void userDidSelectChannel(ChatChannel channel) {

    }

    @Override
    public void userDidSelectPlayer(Player player) {

    }

    @Override
    public Bitmap getImageForPlayer(Context context, Player player) {
        return null;
    }
}
