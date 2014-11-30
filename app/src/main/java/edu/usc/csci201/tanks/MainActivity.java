package edu.usc.csci201.tanks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.android.gms.plus.Plus;
import com.google.example.games.basegameutils.BaseGameUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements GameListFragment.GameListFragmentListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    private static final String PREFS_NAME = "prefs";
    private static final String PROPERTY_NAME = "name";

    private static final int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;

    private GoogleApiClient mGoogleApiClient = null;

    private Firebase usersRef;
    private Firebase gamesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(getApplicationContext());

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new GameListFragment())
                    .commit();
        }

        Log.d(TAG, "onCreate");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        usersRef = new Firebase("https://csci-201-tanks.firebaseio.com/users");
        gamesRef = new Firebase("https://csci-201-tanks.firebaseio.com/gamelist");
    }

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void shouldJoinGame(final String gameName) {
        Log.i(TAG, "shouldJoinGame(\"" + gameName + "\"");
        gamesRef.child(gameName + "/players").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> playerNames = (List<String>) dataSnapshot.getValue();
                if (playerNames == null)
                    playerNames = new LinkedList<String>();
                if (!playerNames.contains(getPlayerName())) {
                    playerNames.add(getPlayerName());
                    dataSnapshot.getRef().setValue(playerNames);
                }

                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra(GameActivity.EXTRA_GAME, gameName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "onConnected");

        Log.i(TAG, "checking firebase to see if user needs to be registered");

        final Player me = Games.Players.getCurrentPlayer(mGoogleApiClient);
        usersRef.child(me.getPlayerId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Map<String, Object> playerMap = new HashMap<String, Object>();
                    playerMap.put("name", me.getDisplayName());
                    playerMap.put("photo", me.getHiResImageUrl());
                    usersRef.child(me.getPlayerId()).setValue(playerMap);
                    setPlayerName(me.getDisplayName());
                    Log.i(TAG, "added user to firebase");
                } else {
                    Log.i(TAG, "user already in firebase");
                }

//                getFragmentManager().beginTransaction()
//                        .replace(R.id.container, new GameListFragment())
//                        .commit();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed");
        if (mResolvingConnectionFailure) {
            // Already resolving
            return;
        }

        // If the sign in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult: " + requestCode);

        if (RC_SIGN_IN == requestCode) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }

    private void setPlayerName(String name) {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putString(PROPERTY_NAME, name).commit();
    }

    private String getPlayerName() {
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(PROPERTY_NAME, "");
    }

}
