package edu.usc.csci201.tanks.network;

/**
 * Created by vmagro on 11/23/14.
 */
public class GameStateUpdateEvent {

    private Game game;

    public GameStateUpdateEvent(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

}