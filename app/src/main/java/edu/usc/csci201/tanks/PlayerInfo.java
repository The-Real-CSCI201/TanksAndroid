package edu.usc.csci201.tanks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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
    private String chatUrl;

    private PlayerListener listener;

    public PlayerInfo(String id, int team, int health, Point location, Direction direction, String name, String imageUrl, String chatUrl) {
        this.id = id;
        this.team = team;
        this.health = health;
        this.location = location;
        this.direction = direction;
        this.name = name;
        this.imageUrl = imageUrl;
        this.chatUrl = chatUrl;
    }

    public PlayerInfo() {

    }

    public void cloneFrom(PlayerInfo playerInfo) {
        if (!this.id.equals(playerInfo.id)) {
            throw new IllegalStateException("Can't clone from player that has a different id");
        }
        this.team = playerInfo.team;
        this.location = playerInfo.location;
        this.health = playerInfo.health;
        this.direction = playerInfo.direction;
        this.name = playerInfo.name;
        this.imageUrl = playerInfo.imageUrl;
        this.chatUrl = playerInfo.chatUrl;
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

    @JsonIgnore
    @JsonIgnoreProperties
    public boolean isAlive() {
        return this.health > 0;
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

    public Bitmap getImage(Context context, int sideLength) throws IOException {
        return Picasso.with(context).load(getImageUrl()).resize(sideLength, sideLength).get();
    }

    @JsonIgnore
    @JsonIgnoreProperties
    public TankType getTankType() {
        if (isMe())
            return TankType.USER;
        else if (isOnMyTeam())
            return TankType.TEAM;
        else
            return TankType.OPPONENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerInfo that = (PlayerInfo) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getChatUrl() {
        return chatUrl;
    }

    public static interface PlayerListener {
        public void onPlayerChange(PlayerInfo playerInfo);
    }
}
