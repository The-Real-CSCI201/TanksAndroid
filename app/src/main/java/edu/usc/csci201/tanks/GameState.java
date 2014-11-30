package edu.usc.csci201.tanks;

import android.graphics.Point;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.List;

/**
 * Created by vmagro on 11/29/14.
 */
public class GameState implements ValueEventListener, PlayerInfo.PlayerListener {

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

    public void moveMe(Point newLocation) {
        gameRef.child("players/" + PlayerInfo.getMyId() + "/location").setValue(newLocation);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.i(TAG, "onDataChange");
        playerInfos = dataSnapshot.child("players").getValue(new GenericTypeIndicator<List<PlayerInfo>>() {
        });
        obstacleLocations = dataSnapshot.child("obstacles").getValue(new GenericTypeIndicator<List<Point>>() {
        });
        for (PlayerInfo p : playerInfos)
            p.setListener(this);
        Log.i(TAG, "onDataChange finished");
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    @Override
    public void onPlayerChange(PlayerInfo playerInfo) {
//        gameRef.child("players").child("")
    }
}
