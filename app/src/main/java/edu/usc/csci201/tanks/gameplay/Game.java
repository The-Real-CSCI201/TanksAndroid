package edu.usc.csci201.tanks.gameplay;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.usc.csci201.tanks.GameState;
import edu.usc.csci201.tanks.PlayerInfo;
import edu.usc.csci201.tanks.common.Direction;
import edu.usc.csci201.tanks.graphics.GameplayInterfaceListener;

/**
 * Created by carrieksun on 11/23/2014.
 */
public class Game implements GameplayInterfaceListener {

    public static final int HEIGHT = 7;
    public static final int WIDTH = 14;

    public Game() {
    }

    //returns player in that position, if none then returns null
    private PlayerInfo getPlayerInPosition(int row, int col) {
        for (PlayerInfo p : getPlayers()) {
            if (p.getLocation().y == row && p.getLocation().x == col)
                return p;
        }
        return null;
    }

    //private methods to help with userCanMoveInDirection()
    private boolean userCanMoveNorth(int row, int col) {
        List<PlayerInfo> players = GameState.getInstance().getPlayerInfos();
        boolean playerPresent = false;
        for (PlayerInfo p : players) {
            if (p.getLocation().equals(new Point(col, row - 1)) && p.isAlive())
                playerPresent = true;
        }
        if (row == 0)//top edge
            return false;
        else if (getObstacles().contains(new Point(col, row - 1)) || playerPresent)
            return false;
        return true;
    }

    private boolean userCanMoveEast(int row, int col) {
        List<PlayerInfo> players = GameState.getInstance().getPlayerInfos();
        boolean playerPresent = false;
        for (PlayerInfo p : players) {
            if (p.getLocation().equals(new Point(col + 1, row)) && p.isAlive())
                playerPresent = true;
        }

        if (col == WIDTH - 1)//right edge
            return false;
        else if (getObstacles().contains(new Point(col + 1, row)) || playerPresent)
            return false;
        return true;
    }

    private boolean userCanMoveSouth(int row, int col) {
        List<PlayerInfo> players = GameState.getInstance().getPlayerInfos();
        boolean playerPresent = false;
        for (PlayerInfo p : players) {
            if (p.getLocation().equals(new Point(col, row + 1)) && p.isAlive())
                playerPresent = true;
        }

        if (row == HEIGHT - 1)//bottom edge
            return false;
        else if (getObstacles().contains(new Point(col, row + 1)) || playerPresent)
            return false;
        return true;
    }

    private boolean userCanMoveWest(int row, int col) {
        List<PlayerInfo> players = GameState.getInstance().getPlayerInfos();
        boolean playerPresent = false;
        for (PlayerInfo p : players) {
            if (p.getLocation().equals(new Point(col - 1, row)) && p.isAlive())
                playerPresent = true;
        }

        if (col == 0)//left edge
            return false;
        else if (getObstacles().contains(new Point(col - 1, row)) || playerPresent)
            return false;
        return true;
    }

    //private methods to help with userDidFireInDirection
    private boolean userDidFireNorth(int row, int col) {
        int rowCount = row - 1;
        while (rowCount >= 0) {
            if (getObstacles().contains(new Point(col, rowCount)))//hit an obstacle, return
            {
                return true;
            } else {
                PlayerInfo p = getPlayerInPosition(rowCount, col);
                if (p != null && !p.isOnMyTeam() && p.isAlive()) {//hit a player
                    p.setHealth(p.getHealth() - 1);
                    return true;
                } else if (p != null && p.isAlive()) {
                    return true;
                }
            }
            rowCount--;
        }
        return false;//reached end of map didn't hit obstacle or player
    }

    private boolean userDidFireEast(int row, int col) {
        int colCount = col + 1;
        while (colCount < WIDTH) {
            if (getObstacles().contains(new Point(colCount, row))) {
                return true;
            } else {
                PlayerInfo p = getPlayerInPosition(row, colCount);
                if (p != null && !p.isOnMyTeam() && p.isAlive()) {
                    p.setHealth(p.getHealth() - 1);
                    return true;
                } else if (p != null && p.isAlive()) {
                    return true;
                }
            }
            colCount++;
        }
        return false;
    }

    private boolean userDidFireSouth(int row, int col) {
        int rowCount = row + 1;
        while (rowCount < WIDTH) {
            if (getObstacles().contains(new Point(col, rowCount)))//hit an obstacle, return
            {
                return true;
            } else {
                PlayerInfo p = getPlayerInPosition(rowCount, col);
                if (p != null && !p.isOnMyTeam() && p.isAlive()) {//hit a player
                    p.setHealth(p.getHealth() - 1);
                    return true;
                } else if (p != null && p.isAlive()) {
                    return true;
                }
            }
            rowCount++;
        }
        return false;
    }

    private boolean userDidFireWest(int row, int col) {
        int colCount = col - 1;
        while (colCount >= 0) {
            if (getObstacles().contains(new Point(colCount, row))) {
                return true;
            } else {
                PlayerInfo p = getPlayerInPosition(row, colCount);
                if (p != null && !p.isOnMyTeam() && p.isAlive()) {
                    p.setHealth(p.getHealth() - 1);
                    return true;
                } else if (p != null && p.isAlive()) {
                    return true;
                }
            }
            colCount--;
        }
        return false;
    }


    @Override
    public int mapWidth() {
        //returns the number of columns the gameMap has
        return WIDTH;
    }

    @Override
    public int mapHeight() {
        return HEIGHT;
    }

    @Override
    public boolean userCanMoveInDirection(Direction direction) {
        PlayerInfo currPlayer = null;
        for (int i = 0; i < getPlayers().size(); i++) {
            PlayerInfo temp = getPlayers().get(i);
            if (temp.isMe()) {
                currPlayer = temp;
                break;
            }
        }
        int row = currPlayer.getLocation().y;
        int col = currPlayer.getLocation().x;

        switch (direction) {
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
    public boolean tileHasObstacle(int row, int col) {
        return false;
    }

    @Override
    public void userDidMoveInDirection(Direction direction) {
        PlayerInfo currPlayer = null;
        for (PlayerInfo temp : GameState.getInstance().getPlayerInfos()) {
            if (temp.isMe()) {
                currPlayer = temp;
                break;
            }
        }
        int row = currPlayer.getLocation().y;
        int col = currPlayer.getLocation().x;
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                turnListener.takeTurn();
            }
        }, 1000);

        switch (direction) {
            case NORTH:
                GameState.getInstance().moveMe(new Point(col, row - 1));
                return;
            case EAST:
                GameState.getInstance().moveMe(new Point(col + 1, row));
                return;
            case SOUTH:
                GameState.getInstance().moveMe(new Point(col, row + 1));
                return;
            case WEST:
                GameState.getInstance().moveMe(new Point(col - 1, row));
                return;
            default://shouldn't happen, all cases covered
                return;
        }
    }


    @Override
    public boolean userDidFireInDirection(Direction direction) {
        PlayerInfo currPlayer = null;
        for (int i = 0; i < getPlayers().size(); i++) {
            PlayerInfo temp = getPlayers().get(i);
            if (temp.isMe()) {
                currPlayer = temp;
                break;
            }
        }
        int row = currPlayer.getLocation().y;
        int col = currPlayer.getLocation().x;

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                turnListener.takeTurn();
            }
        }, 1000);

        switch (direction) {
            case NORTH:
                return userDidFireNorth(row, col);
            case SOUTH:
                return userDidFireSouth(row, col);
            case WEST:
                return userDidFireWest(row, col);
            case EAST:
                return userDidFireEast(row, col);
        }
        return false;
    }

    @Override
    public int timeRemainingInCurrentTurn() {
        return 0;
    }

    @Override
    public int numberOfPlayers() {
        return getPlayers().size();
    }

    @Override
    public String[] getPlayerNames() {
        ArrayList<String> listOfNames = new ArrayList<String>();
        for (PlayerInfo p : getPlayers()) {
            listOfNames.add(p.getName());
        }
        return (String[]) listOfNames.toArray();
    }

    public boolean gameIsFinished() {
        int team0Alive = 0;
        int team1Alive = 0;
        for (PlayerInfo p : getPlayers()) {
            if (p.getTeam() == 0) {
                if (p.isAlive())
                    team0Alive++;
            } else //p.getTeam() == 1
            {
                if (p.isAlive()) {
                    team1Alive++;
                }
            }
        }
        if (team0Alive == 0 || team1Alive == 0)     //if either team has no alive players remaining
        {
            return true;
        }
        return false;
    }


    @Override
    public List<PlayerInfo> getPlayers() {
        return GameState.getInstance().getPlayerInfos();
    }

    public List<Point> getObstacles() {
        return GameState.getInstance().getObstacleLocations();
    }

    public interface TurnListener {
        public void takeTurn();
    }

    public TurnListener turnListener;
}
