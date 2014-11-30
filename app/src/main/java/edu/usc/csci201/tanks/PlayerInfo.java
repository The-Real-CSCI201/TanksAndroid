package edu.usc.csci201.tanks;

import android.graphics.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.usc.csci201.tanks.common.Direction;
import edu.usc.csci201.tanks.common.TankType;

/**
 * Created by vmagro on 11/29/14.
 */
public class PlayerInfo {

    public static final int MAX_HEALTH = 10;
    private static int myTeam = -1;
    private static String myId = null;

    public static void setMyTeam(int team) {
        myTeam = team;
    }

    public static void setMyId(String id) {
        myId = id;
    }

    public static String getMyId() {
        return myId;
    }

    private String id;
    private int team;
    private Point location;
    private int health;
    private Direction direction;
    private String name;
    private String imageUrl;
    private PlayerListener listener;
    private TankType type;

    public PlayerInfo(String id, int team, int health, Point location, Direction direction, String name, String imageUrl) {
        this.id = id;
        this.team = team;
        this.health = health;
        this.location = location;
        this.direction = direction;
        this.name = name;
        this.imageUrl = imageUrl;
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

    public void setHealth(int health) {
        if (listener != null)
            listener.onPlayerChange(this);
        this.health = health;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if (listener != null)
            listener.onPlayerChange(this);
        this.direction = direction;
    }

    public void setListener(PlayerListener listener) {
        this.listener = listener;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public TankType getTankType() {
        return type;
    }

    public static interface PlayerListener {
        public void onPlayerChange(PlayerInfo playerInfo);
    }
}
