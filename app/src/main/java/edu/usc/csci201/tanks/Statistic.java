package edu.usc.csci201.tanks;

/**
 * Created by vmagro on 12/1/14.
 */
public class Statistic {
    private String playerId;
    private int hits;
    private int kills;

    public Statistic(String playerId, int hits, int kills) {
        this.playerId = playerId;
        this.hits = hits;
        this.kills = kills;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getHits() {
        return hits;
    }

    public int getKills() {
        return kills;
    }
}
