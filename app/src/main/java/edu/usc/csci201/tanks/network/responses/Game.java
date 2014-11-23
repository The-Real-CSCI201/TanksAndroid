package edu.usc.csci201.tanks.network.responses;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private List<String> players = new ArrayList<String>();

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
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