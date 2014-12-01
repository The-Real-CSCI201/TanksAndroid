package edu.usc.csci201.tanks;

import android.graphics.Point;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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

    private GameState() {

    }

    private Firebase gameRef;

    private List<Point> obstacleLocations = Collections.synchronizedList(new LinkedList<Point>());
    private Set<PlayerInfo> playerInfos = Collections.synchronizedSet(new HashSet<PlayerInfo>());
    private Lock playerInfosLock = new ReentrantLock();

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
        playerInfosLock.lock();
        List<PlayerInfo> playerInfosList = new LinkedList<PlayerInfo>();
        playerInfosList.addAll(playerInfos);
        playerInfosLock.unlock();
        return playerInfosList;
    }

    public void moveMe(Point newLocation) {
        gameRef.child("players/" + PlayerInfo.getMyId() + "/location").setValue(newLocation);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.i(TAG, "onDataChange");

        playerInfosLock.lock();
        playerInfos.clear();
        for (DataSnapshot playerSnapshot : dataSnapshot.child("players").getChildren()) {
            playerInfos.add(playerSnapshot.getValue(PlayerInfo.class));
        }
        playerInfosLock.unlock();

        obstacleLocations.clear();
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
        Log.i(TAG, "updating player data for " + playerInfo.getName() + " to firebase " + playerInfo.getHealth());
        gameRef.child("players").child(playerInfo.getId()).setValue(playerInfo);
    }

    public void waitForData() throws InterruptedException {
        Log.i(TAG, "waitForData locking firebaseDataLock");
        firebaseDataLoadedLock.lock();
        dataLoadedCondition.await();
        Log.i(TAG, "waitForData unlocking firebaseDataLock");
        firebaseDataLoadedLock.unlock();
    }

    public PlayerInfo getPlayer(String id) {
        for (PlayerInfo playerInfo : getPlayerInfos()) {
            if (playerInfo.getId().equals(id))
                return playerInfo;
        }
        return null;
    }

    public PlayerInfo getMe() {
        return getPlayer(PlayerInfo.getMyId());
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (playerAddedListener != null) {
            PlayerInfo playerInfo = dataSnapshot.getValue(PlayerInfo.class);
            playerInfos.add(playerInfo);
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
