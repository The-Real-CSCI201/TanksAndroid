package edu.usc.csci201.tanks.graphics;

import java.util.List;

import edu.usc.csci201.tanks.PlayerInfo;
import edu.usc.csci201.tanks.common.Direction;

/**
 * (C) 2014 nickentin
 * Created on 11/23/14.
 */
public interface GameplayInterfaceListener {
    // gameplay controls
    public void userDidPauseGame();
    public void userDidResumeGame();
    public void userDidQuitGame();

    // map data responders
    public int mapWidth();                                          // get map width (number of horizontal tiles)
    public int mapHeight();                                         // get map height (number of vertical tiles)


    // turn actions
    public boolean userCanMoveInDirection(Direction direction);     // returns whether a move is the specified direction is valid
    public void userDidMoveInDirection(Direction direction);        // called when the user chooses to move in a certain direction
    public boolean userDidFireInDirection(Direction direction);     // called when the user chooses to fire in a certain direction
                                                                    // returns whether the bullet should hit its target

    // gameplay data responders
    public int timeRemainingInCurrentTurn();                        // time to show on countdown clock (in seconds)
    public int numberOfPlayers();                                   // number of players
    public String[] getPlayerNames();                               // get player names (NOTE: current user should always be first item)
    public List<PlayerInfo> getPlayers();                                   // get array of edu.usc.csci201.tanks.gameplay.Player objects

}
