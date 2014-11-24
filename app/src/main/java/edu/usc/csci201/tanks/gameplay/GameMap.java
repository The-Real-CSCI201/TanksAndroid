package edu.usc.csci201.tanks.gameplay;

/**
 * Created by carrieksun on 11/23/2014.
 */
public class GameMap {
    private String id;
    private Tile[][] grid;
    public GameMap(String id)
    {
        this.id = id;
        //TODO: implement constructor? what else does it need?
    }
    public Tile getTile(int row, int col)
    {
        return grid[row][col];
    }

}
