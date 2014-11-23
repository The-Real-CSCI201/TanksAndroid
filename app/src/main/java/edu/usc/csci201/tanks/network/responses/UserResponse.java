package edu.usc.csci201.tanks.network.responses;

import com.google.gson.annotations.Expose;

public class UserResponse {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String gcmId;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
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
     * @return The gcmId
     */
    public String getGcmId() {
        return gcmId;
    }

    /**
     * @param gcmId The gcmId
     */
    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

}