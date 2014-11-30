package edu.usc.csci201.tanks;

import android.graphics.Point;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by vmagro on 11/29/14.
 */
public class GameState implements ValueEventListener, ChildEventListener, PlayerInfo.PlayerListener {

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

    private PlayerAddedListener playerAddedListener;

    private Lock firebaseDataLoadedLock = new ReentrantLock();
    private Condition dataLoadedCondition = firebaseDataLoadedLock.newCondition();

    public void init(Firebase gameRef) {
        this.gameRef = gameRef;
        this.gameRef.addValueEventListener(this);
        this.gameRef.child("players").addChildEventListener(this);
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
        playerInfos = new LinkedList<PlayerInfo>();
        for (DataSnapshot playerSnapshot : dataSnapshot.child("players").getChildren()) {
            playerInfos.add(playerSnapshot.getValue(PlayerInfo.class));
        }
        obstacleLocations = new LinkedList<Point>();
        for (DataSnapshot obstacleSnapshot : dataSnapshot.child("obstacles").getChildren()) {
            obstacleLocations.add(obstacleSnapshot.getValue(Point.class));
        }
        for (PlayerInfo p : playerInfos)
            p.setListener(this);
        Log.i(TAG, "onDataChange finished");

        Log.i(TAG, "onDataChange locking firebaseDataLock");
        firebaseDataLoadedLock.lock();
        dataLoadedCondition.signalAll();
        Log.i(TAG, "onDataChange unlocking firebaseDataLock");
        firebaseDataLoadedLock.unlock();
    }

    @Override
    public void onPlayerChange(PlayerInfo playerInfo) {
        gameRef.child("players").child(playerInfo.getId()).setValue(playerInfo);
    }

    public void waitForData() throws InterruptedException {
        Log.i(TAG, "waitForData locking firebaseDataLock");
        firebaseDataLoadedLock.lock();
        dataLoadedCondition.await();
        Log.i(TAG, "waitForData unlocking firebaseDataLock");
        firebaseDataLoadedLock.unlock();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (playerAddedListener != null) {
            PlayerInfo playerInfo = dataSnapshot.getValue(PlayerInfo.class);
            playerAddedListener.playerAdded(playerInfo);
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
    }

    public void setPlayerAddedListener(PlayerAddedListener playerAddedListener) {
        this.playerAddedListener = playerAddedListener;
    }

    public static interface PlayerAddedListener {
        public void playerAdded(PlayerInfo addedPlayer);
    }
}
