package edu.usc.csci201.tanks;

import android.graphics.Point;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

/**
 * Created by vmagro on 11/29/14.
 */
public class GameState implements ValueEventListener {

    private static final String TAG = "GameState";

    private static GameState instance;

    public static synchronized GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    private Firebase gameRef;

    private List<Point> obstacleLocations;
    private List<PlayerInfo> playerInfos;
    private List<Point> bullets;

    public void init(Firebase gameRef) {
        this.gameRef = gameRef;
        this.gameRef.addValueEventListener(this);
    }

    public List<Point> getObstacleLocations() {
        return obstacleLocations;
    }

    public List<PlayerInfo> getPlayerInfos() {
        return playerInfos;
    }

    public List<Point> getBullets() {
        return bullets;
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

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.i(TAG, "onDataChange");
        playerInfos = (List<PlayerInfo>) dataSnapshot.child("players").getValue();
        obstacleLocations = (List<Point>) dataSnapshot.child("obstacles").getValue();
        bullets = (List<Point>) dataSnapshot.child("bullets").getValue();
        Log.i(TAG, "onDataChange finished");
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
