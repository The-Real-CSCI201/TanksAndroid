package edu.usc.csci201.tanks.gameplay;

/**
 * Created by carrieksun on 11/23/2014.
 */
public class Player {
    private String id;
    private String name;
    private int health;//for math simplicity's sake, health is int
    private int row, col;
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
    public void setHealth(int health)
    {
        this.health = health;
    }
}
