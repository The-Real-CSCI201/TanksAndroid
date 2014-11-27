package edu.usc.csci201.tanks.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vmagro on 11/27/14.
 */
public class Conference {

    @SerializedName("conference_name")
    @Expose
    private String conferenceName;
    @SerializedName("conference_url")
    @Expose
    private String conferenceUrl;

    /**
     * @return The conferenceName
     */
    public String getConferenceName() {
        return conferenceName;
    }

    /**
     * @param conferenceName The conference_name
     */
    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    /**
     * @return The conferenceUrl
     */
    public String getConferenceUrl() {
        return conferenceUrl;
    }

    /**
     * @param conferenceUrl The conference_url
     */
    public void setConferenceUrl(String conferenceUrl) {
        this.conferenceUrl = conferenceUrl;
    }
}
