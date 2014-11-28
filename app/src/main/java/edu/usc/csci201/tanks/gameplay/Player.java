package edu.usc.csci201.tanks.gameplay;

import edu.usc.csci201.tanks.common.TankType;

/**
 * Created by carrieksun on 11/23/2014.
 */
public class Player {
    public static final int MAX_HEALTH = 10;

    private String id;
    private String name;
    private int health;//for math simplicity's sake, health is int
    private int row, col;
    private TankType type;
    //getters
    public Player()
    {
        //TODO: implement constructor
    }
    public String getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public int getHealth()
    {
        return health;
    }
    public int getRow()
    {
        return row;
    }
    public int getCol()
    {
        return col;
    }
    public TankType getTankType() { return type; }
    public void setHealth(int health)
    {
        this.health = health;
    }
}
