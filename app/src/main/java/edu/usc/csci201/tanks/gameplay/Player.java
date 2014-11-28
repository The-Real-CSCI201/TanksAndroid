package edu.usc.csci201.tanks.gameplay;

import java.util.List;

/**
 * Created by carrieksun on 11/23/2014.
 */
public class Player {

    private static Player me;

    private String id;
    private String name;
    private String photoUrl;
    private int health;//for math simplicity's sake, health is int
    private int row, col;
    private List<Player> teammates;

    //getters
    public Player(String id, String name, String photoUrl, int row, int col, List<Player> teammates) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.row = row;
        this.col = col;
        this.teammates = teammates;
    }

    public static Player me() {
        return me;
    }

    public static void setMe(Player player) {
        me = player;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public List<Player> getTeammates() {
        return teammates;
    }
}
