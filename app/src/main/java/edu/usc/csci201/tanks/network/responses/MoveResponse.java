package edu.usc.csci201.tanks.network.responses;

import com.google.gson.annotations.Expose;

public class MoveResponse {

    @Expose
    private String status;
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
