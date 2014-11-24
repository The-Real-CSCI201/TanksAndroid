package edu.usc.csci201.tanks.graphics;

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
    public int mapWidth();
    public int mapHeight();

    // turn actions
    public boolean userCanMoveInDirection(Direction direction);
    public void userDidMoveInDirection(Direction direction);
    public boolean userDidFireInDirection(Direction direction);

    // gameplay time responder
    public int timeRemainingInCurrentTurn();

}
