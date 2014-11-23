package edu.usc.csci201.tanks.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class GcmBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "GCM_BROADCAST_RECEIVER";

    public GcmBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "received message");
        Toast.makeText(context.getApplicationContext(), "Received message", Toast.LENGTH_LONG).show();
    }
}
