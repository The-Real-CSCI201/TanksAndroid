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

    //TODO: user methods
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
    public boolean tileHasNorthWall(int row, int col) {
        return gameMap.getTile(row, col).hasNorthWall();
    }

    @Override
    public boolean tileHasEastWall(int row, int col) {
        return gameMap.getTile(row, col).hasEastWall();
    }

    @Override
    public boolean tileHasSouthWall(int row, int col) {
        return gameMap.getTile(row, col).hasSouthWall();
    }

    @Override
    public boolean tileHasWestWall(int row, int col) {
        return gameMap.getTile(row, col).hasWestWall();
    }

    @Override
    public boolean userCanMoveInDirection(Direction direction) {
        return false;
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
