package edu.usc.csci201.tanks.graphics;

import edu.usc.csci201.tanks.common.Direction;
import edu.usc.csci201.tanks.common.TankType;
import edu.usc.csci201.tanks.gameplay.Player;

/**
 * (C) 2014 nickentin
 * Created on 11/24/14.
 */
public class DebugGameListener implements GameplayInterfaceListener {
    @Override
    public void userDidPauseGame() {
        System.out.println("User did pause game");
    }

    @Override
    public void userDidResumeGame() {
        System.out.println("User did resume game");
    }

    @Override
    public void userDidQuitGame() {
        System.out.println("Use did quit game");
    }

    @Override
    public int mapWidth() {
        return 14;
    }

    @Override
    public int mapHeight() {
        return 7;
    }

    @Override
    public boolean tileHasNorthWall(int row, int col) {
        return false;
    }

    @Override
    public boolean tileHasEastWall(int row, int col) {
        return false;
    }

    @Override
    public boolean tileHasSouthWall(int row, int col) {
        return false;
    }

    @Override
    public boolean tileHasWestWall(int row, int col) {
        return false;
    }

    @Override
    public boolean userCanMoveInDirection(Direction direction) {
        return true;
    }

    @Override
    public void userDidMoveInDirection(Direction direction) {
        System.out.println("User did perform move");
    }

    @Override
    public boolean userDidFireInDirection(Direction direction) {
        System.out.println("User did perform fire");
        return true;
    }

    @Override
    public int timeRemainingInCurrentTurn() {
        return 83;
    }

    @Override
    public int numberOfPlayers() {
        return 4;
    }

    @Override
    public String[] getPlayerNames() {
        return new String[]{"Self","Mate","Other","Person"};
    }

    @Override
    public Player[] getPlayers() {
        return new Player[]{
                new Player() {
                    public int getRow() { return 1; }
                    public int getCol() { return 1; }
                    public TankType getTankType() { return TankType.USER; }
                },
                new Player() {
                    public int getRow() { return 2; }
                    public int getCol() { return 2; }
                    public TankType getTankType() { return TankType.TEAM; }
                },
                new Player() {
                    public int getRow() { return 1; }
                    public int getCol() { return 3; }
                    public TankType getTankType() { return TankType.OPPONENT; }
                },
                new Player() {
                    public int getRow() { return 2; }
                    public int getCol() { return 4; }
                    public TankType getTankType() { return TankType.OPPONENT; }
                }
        };
    }
}
