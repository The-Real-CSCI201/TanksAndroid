package edu.usc.csci201.tanks.network;

import edu.usc.csci201.tanks.network.responses.Game;

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