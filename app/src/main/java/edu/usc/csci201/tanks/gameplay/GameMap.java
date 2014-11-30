package edu.usc.csci201.tanks.gameplay;

/**
 * Created by carrieksun on 11/23/2014.
 */
public class GameMap {
    private String id;
    private boolean[][] obstacleMap;
    public GameMap(String id)
    {
        this.id = id;
        //TODO: implement constructor? what else does it need?
    }
    public boolean hasObstacle(int row, int col)
    {
        return obstacleMap[row][col];
    }
    public int getWidth()//returns number of columns
    {
        return obstacleMap[0].length;
    }
    public int getHeight()//returns number of rows
    {
        return obstacleMap.length;
    }
}
