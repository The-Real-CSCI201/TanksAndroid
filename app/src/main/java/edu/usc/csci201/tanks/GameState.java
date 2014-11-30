package edu.usc.csci201.tanks;

import android.graphics.Point;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by vmagro on 11/29/14.
 */
public class GameState implements ValueEventListener, PlayerInfo.PlayerListener {

    private static final String TAG = "GameState";

    private static GameState instance;

    private Lock firebaseDataLoadedLock = new ReentrantLock();
    private Condition dataLoadedCondition = firebaseDataLoadedLock.newCondition();

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

        Log.i(TAG, "locking firebaseDataLock");
        firebaseDataLoadedLock.lock();
        dataLoadedCondition.signalAll();
        Log.i(TAG, "unlocking firebaseDataLock");
        firebaseDataLoadedLock.unlock();
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    @Override
    public void onPlayerChange(PlayerInfo playerInfo) {
        gameRef.child("players").child(playerInfo.getId()).setValue(playerInfo);
    }

    public void waitForData() throws InterruptedException {
        Log.i(TAG, "locking firebaseDataLock");
        firebaseDataLoadedLock.lock();
        dataLoadedCondition.await();
        Log.i(TAG, "unlocking firebaseDataLock");
        firebaseDataLoadedLock.unlock();
    }
}
