package edu.usc.csci201.tanks;

import android.graphics.Point;

/**
 * Created by vmagro on 11/29/14.
 */
public class PlayerInfo {

    private final boolean isMe;
    private final boolean isOnMyTeam;
    private final boolean isOnOpposingTeam;
    private final Point location;

    public PlayerInfo(boolean isMe, boolean isOnMyTeam, boolean isOnOpposingTeam, Point location) {
        this.isMe = isMe;
        this.isOnMyTeam = isOnMyTeam;
        this.isOnOpposingTeam = isOnOpposingTeam;
        this.location = location;
    }

    public boolean isMe() {
        return isMe;
    }

    public boolean isOnMyTeam() {
        return isOnMyTeam;
    }

    public boolean isOnOpposingTeam() {
        return isOnOpposingTeam;
    }

    public Point getLocation() {
        return location;
    }
}
