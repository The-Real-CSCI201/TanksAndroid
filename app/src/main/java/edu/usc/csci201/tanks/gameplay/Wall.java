package edu.usc.csci201.tanks.gameplay;

import edu.usc.csci201.tanks.common.Orientation;

/**
 * Created by carrie on 11/24/14.
 */
public class Wall {
    private int row, col;
    private Orientation orientation;
    public Wall(int row, int col, Orientation orientation)
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
    public Orientation getOrientation()
    {
        return orientation;
    }
}
