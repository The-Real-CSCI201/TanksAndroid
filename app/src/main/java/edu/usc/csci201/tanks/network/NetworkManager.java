package edu.usc.csci201.tanks.network;

/**
 * Created by vmagro on 11/23/14.
 */
public class NetworkManager {

    private static NetworkManager instance;

    public static synchronized NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager() {

    }

}
