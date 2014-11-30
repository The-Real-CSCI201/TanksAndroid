package edu.usc.csci201.tanks.gameplay;

import java.util.*;

import edu.usc.csci201.tanks.common.Direction;
import edu.usc.csci201.tanks.graphics.GameplayInterfaceListener;

/**
 * Created by carrieksun on 11/23/2014.
 */
public class Game implements GameplayInterfaceListener{
    private List<Player> playerList;
    private GameMap gameMap;

    public Game() {
    }

    //private methods to help with userCanMoveInDirection()
    private boolean userCanMoveNorth(int row, int col)
    {
        if (row == 0)//top edge
            return false;
        else if (gameMap.hasObstacle(row - 1, col))
            return false;
        return true;
    }
    private boolean userCanMoveEast(int row, int col)
    {
        if (col == gameMap.getWidth()-1)//right edge
            return false;
        else if (gameMap.hasObstacle(row, col + 1))
            return false;
        return true;
    }
    private boolean userCanMoveSouth(int row, int col)
    {
        if (row == gameMap.getHeight() - 1)//bottom edge
            return false;
        else if (gameMap.hasObstacle(row + 1, col))
            return false;
        return true;
    }
    private boolean userCanMoveWest(int row, int col)
    {
        if (col == 0)//left edge
            return false;
        else if (gameMap.hasObstacle(row, col - 1))
            return false;
        return true;
    }
    @Override
    public void userDidPauseGame() {

    }

    @Override
    public void userDidResumeGame() {

    }

    @Override
    public void userDidQuitGame() {

    }

    @Override
    public int mapWidth() {
        //returns the number of columns the gameMap has
        return gameMap.getWidth();
    }

    @Override
    public int mapHeight() {
        return gameMap.getHeight();
    }



    @Override
    public boolean userCanMoveInDirection(Direction direction) {

        Player currPlayer = playerList.get(0);
        int row = currPlayer.getRow();
        int col = currPlayer.getCol();

        switch (direction)
        {
            case NORTH:
                return userCanMoveNorth(row, col);
            case EAST:
                return userCanMoveEast(row, col);
            case SOUTH:
                return userCanMoveSouth(row, col);
            case WEST:
                return userCanMoveWest(row, col);
            default://shouldn't happen, all cases covered
                return false;
        }
    }

    @Override
    public void userDidMoveInDirection(Direction direction) {

    }

    @Override
    public boolean userDidFireInDirection(Direction direction) {
        return false;
    }

    @Override
    public int timeRemainingInCurrentTurn() {
        return 0;
    }

    @Override
    public int numberOfPlayers() {
        return playerList.size();
    }

    @Override
    public String[] getPlayerNames() {
        ArrayList<String> listOfNames = new ArrayList<String>();
        for (Player p : playerList)
        {
            listOfNames.add(p.getName());
        }
        return (String[])listOfNames.toArray();
    }

    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }
}
