package edu.usc.csci201.tanks.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import edu.usc.csci201.tanks.BusManager;
import edu.usc.csci201.tanks.network.responses.Game;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GcmBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "GCM_BROADCAST_RECEIVER";

    public GcmBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "received message");
        Toast.makeText(context.getApplicationContext(), "Received message", Toast.LENGTH_LONG).show();
        //download the latest game state from the server
        String messageType = GoogleCloudMessaging.getInstance(context).getMessageType(intent);
        Bundle extras = intent.getExtras();
        /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
        if (GoogleCloudMessaging.
                MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            Log.w(TAG, "send error");
        } else if (GoogleCloudMessaging.
                MESSAGE_TYPE_DELETED.equals(messageType)) {
            Log.w(TAG, "message deleted on server");
            // If it's a regular GCM message, do some work.
        } else if (GoogleCloudMessaging.
                MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            String gameId = extras.getString("game");
            Log.d(TAG, "getting game info " + gameId);
            TanksApi.TanksApi.getGame(gameId, new Callback<Game>() {
                @Override
                public void success(Game game, Response response) {
                    Log.d(TAG, "sending game status update on bus");
                    BusManager.getBus().post(new GameStateUpdateEvent(game));
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }
    }
}
