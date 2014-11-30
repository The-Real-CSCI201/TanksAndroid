package edu.usc.csci201.tanks;

import android.graphics.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vmagro on 11/29/14.
 */
public class PlayerInfo {

    private static int myTeam = -1;
    private static String myId = null;

    public static void setMyTeam(int team) {
        myTeam = team;
    }

    public static void setMyId(String id) {
        myId = id;
    }

    private String id;
    private int team;
    private Point location;
    private int health;

    public PlayerInfo(String id, int team, int health, Point location) {
        this.id = id;
        this.team = team;
        this.health = health;
        this.location = location;
    }

    public PlayerInfo() {

    }

    public Point getLocation() {
        return location;
    }

    public int getTeam() {
        return team;
    }

    public String getId() {
        return id;
    }

    @JsonIgnore
    @JsonIgnoreProperties
    public boolean isMe() {
        return id.equals(myId);
    }

    @JsonIgnore
    @JsonIgnoreProperties
    public boolean isOnMyTeam() {
        return team == myTeam;
    }

    @JsonIgnore
    @JsonIgnoreProperties
    public boolean isOnOpposingTeam() {
        return !isOnMyTeam();
    }

    public int getHealth() {
        return health;
    }
}
