package edu.usc.csci201.tanks.network;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {

    @Expose
    private String name;
    @Expose
    private List<String> players = new ArrayList<String>();

    public Game() {

    }

    public Game(String name, List<String> players) {
        this.name = name;
        this.players = players;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The players
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     * @param players The players
     */
    public void setPlayers(List<String> players) {
        this.players = players;
    }

}