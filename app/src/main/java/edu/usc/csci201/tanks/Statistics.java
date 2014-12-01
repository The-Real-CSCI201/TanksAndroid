package edu.usc.csci201.tanks;

import android.content.Context;
import android.content.Intent;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vmagro on 11/30/14.
 */
public class Statistics implements ValueEventListener {

    private static Statistics instance;

    public static Statistics getInstance() {
        if (instance == null)
            instance = new Statistics();
        return instance;
    }

    private Firebase statsRef;
    private String gameId;
    private Map<String, Integer> hitCounts = new HashMap<String, Integer>();
    private Map<String, Integer> killCounts = new HashMap<String, Integer>();

    private Statistics() {

    }

    /**
     * Init with a stats ref for this game
     *
     * @param statsRef
     */
    public void init(Firebase statsRef) {
        this.statsRef = statsRef;
        this.gameId = statsRef.getKey();
        this.statsRef.addValueEventListener(this);
    }

    public Intent getIntent(Context context) {
        return StatisticsActivity.getStatisticsIntentForGame(context, gameId);
    }

    public void registerHitByPlayer(PlayerInfo player) {
        statsRef.child(player.getId()).child("hits").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }

                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                //This method will be called once with the results of the transaction.
            }
        });
    }

    public void registerKillByPlayer(PlayerInfo player) {
        statsRef.child(player.getId()).child("kills").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }

                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                //This method will be called once with the results of the transaction.
            }
        });
    }

    public int getHitCount(PlayerInfo player) {
        return hitCounts.get(player.getId());
    }

    public int getKillCount(PlayerInfo player) {
        return killCounts.get(player.getId());
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        hitCounts.clear();
        killCounts.clear();
        for (DataSnapshot playerStats : dataSnapshot.getChildren()) {
            hitCounts.put(playerStats.getKey(), (Integer) dataSnapshot.child("hits").getValue());
            killCounts.put(playerStats.getKey(), (Integer) dataSnapshot.child("kills").getValue());
        }
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

}
