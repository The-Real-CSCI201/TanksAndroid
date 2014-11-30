package edu.usc.csci201.tanks;

import android.graphics.Point;

import com.firebase.client.Firebase;

import java.util.List;

/**
 * Created by vmagro on 11/29/14.
 */
public class GameState {

    private static GameState instance;

    public static synchronized GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    private Firebase gameRef;
    private Firebase playersRef;
    private Firebase obstaclesRef;

    public void init(Firebase gameRef) {
        this.gameRef = gameRef;
        this.playersRef = gameRef.child("players");
    }

    public List<Point> getObstacleLocations() {
        //TODO: implement this
        return null;
    }

    public List<PlayerInfo> getPlayerInfos() {
        //TODO: implement this
        return null;
    }

    public void moveMe(Point newLocation) {
        //TODO: implement this
    }

    public String newBullet(Point location) {
        //TODO: implement this
        return null;
    }

    public void moveBullet(String bulletId, Point newLocation) {
        //TODO: implement this
    }

}
