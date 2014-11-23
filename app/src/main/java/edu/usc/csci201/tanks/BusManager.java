package edu.usc.csci201.tanks;

import com.squareup.otto.Bus;

/**
 * Created by vmagro on 11/23/14.
 */
public class BusManager {

    private static Bus bus = new Bus();

    public static synchronized Bus getBus() {
        return bus;
    }

    private BusManager() {

    }

}
