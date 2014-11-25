package edu.usc.csci201.tanks.gameplay;

/**
 * Created by carrie on 11/24/14.
 */
public class Wall {
    private int row, col;
    private int orientation;
    public Wall(int row, int col, int orientation)
    {
        this.row = row;
        this.col = col;
        this.orientation = orientation;
    }
    public int getRow()
    {
        return row;
    }
    public int getCol()
    {
        return col;
    }
    public int getOrientation()
    {
        //return something based on the enum
        return orientation;//subject to change?
    }
}
