package edu.usc.csci201.tanks.graphics;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import edu.usc.csci201.tanks.PlayerInfo;
import edu.usc.csci201.tanks.common.Direction;
import edu.usc.csci201.tanks.common.TankType;

/**
 * (C) 2014 nickentin
 * Created on 11/24/14.
 */
public class DebugGameListener implements GameplayInterfaceListener {

    @Override
    public int mapWidth() {
        return 14;
    }

    @Override
    public int mapHeight() {
        return 7;
    }

    @Override
    public boolean tileHasObstacle(int row, int col) {
        return (row == 5 && col == 3) || (row == 5 && col == 5);
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
        return new String[]{"Self", "Mate", "Other", "Person"};
    }

    @Override
    public List<PlayerInfo> getPlayers() {
        List<PlayerInfo> players = new ArrayList<PlayerInfo>();
        players.add(new PlayerInfo() {
            public Point getLocation() {
                return new Point(1, 1);
            }

            public TankType getTankType() {
                return TankType.USER;
            }

            public int getHealth() {
                return 10;
            }
        });
        players.add(new PlayerInfo() {
            public Point getLocation() {
                return new Point(2, 2);
            }

            public TankType getTankType() {
                return TankType.TEAM;
            }

            public int getHealth() {
                return 7;
            }
        });
        players.add(new PlayerInfo() {
            public Point getLocation() {
                return new Point(3, 1);
            }

            public TankType getTankType() {
                return TankType.OPPONENT;
            }

            public int getHealth() {
                return 5;
            }
        });
        players.add(new PlayerInfo() {
            public Point getLocation() {
                return new Point(4, 2);
            }

            public TankType getTankType() {
                return TankType.OPPONENT;
            }

            public int getHealth() {
                return 2;
            }
        });
        return players;
    }

    ;
}


