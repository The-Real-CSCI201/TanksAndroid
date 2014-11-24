package edu.usc.csci201.tanks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import edu.usc.csci201.tanks.network.GcmBroadcastReceiver;
import edu.usc.csci201.tanks.network.TanksApi;
import edu.usc.csci201.tanks.network.responses.Game;
import edu.usc.csci201.tanks.network.responses.JoinResponse;
import edu.usc.csci201.tanks.network.responses.UserResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity implements GameListFragment.GameListFragmentListener {

    private static final String TAG = "MainActivity";
    private static final String PROPERTY_REG_ID = "regid";
    private static final String PROPERTY_APP_VERSION = "appver";
    private static final String PROPERTY_USER_ID = "userid";

    //project number taken from Google Developers Cloud Console
    private static final String SENDER_ID = "755084857544";

    private GoogleCloudMessaging gcm;
    private String regid;

    private GcmBroadcastReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new GameListFragment())
                    .commit();
        }

        Log.d(TAG, "onCreate");

        //GCM stuff
        gcm = GoogleCloudMessaging.getInstance(this);
        regid = getRegistrationId(this);
        if (regid.isEmpty()) {
            registerInBackground();
            //TODO: probably want to show some kind of loading spinner here while registering with gcm and our server
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        regid = getRegistrationId(this);
        if (!regid.isEmpty()) {
            registerReceiver();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (receiver != null) {
            Log.d(TAG, "unregistering receiver");
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p/>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    sendRegistrationIdToBackend(regid);

                    // Persist the regID - no need to register again.
                    storeRegistrationId(MainActivity.this, regid);

                    registerReceiver();
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                //TODO: want to dismiss any kind of registration progress display
                Log.i(TAG, msg);
            }
        }.execute(null, null, null);
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
     * or CCS to send messages to your app. Not needed for this demo since the
     * device sends upstream messages to a server that echoes back the message
     * using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend(String registrationId) {
        //TODO: get name from google apis
        String userId = getUserId(this);
        //if we have already registered with our server, don't register again, just update the gcm id
        if (!userId.isEmpty()) {
            TanksApi.TanksApi.updateUserGcmId(userId, registrationId);
        } else {
            UserResponse user = TanksApi.TanksApi.registerUser("Vinnie", registrationId);
            storeUserId(this, user.getId());
        }
    }

    /**
     * Stores the user ID in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param userId  user ID returned from server
     */
    private void storeUserId(Context context, String userId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_USER_ID, userId);
        editor.commit();
    }

    /**
     * Gets the current user ID for application on our server.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return user ID, or empty string if there is no existing
     * registration ID.
     */
    private String getUserId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        return prefs.getString(PROPERTY_USER_ID, "");
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId   registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private void registerReceiver() {
        Log.d(TAG, "Registering GcmBroadcastReceiver");
        Log.d(TAG, "registration id = " + getRegistrationId(this));
        receiver = new GcmBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.google.android.c2dm.intent.RECEIVE");
        filter.addCategory("edu.usc.csci201.tanks");
        registerReceiver(receiver, filter, "com.google.android.c2dm.permission.SEND", null);
    }

    @Override
    public void shouldJoinGame(final Game game) {
        TanksApi.TanksApi.joinGame(game.getId(), getUserId(this), new Callback<JoinResponse>() {
            @Override
            public void success(JoinResponse joinResponse, Response response) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra(GameActivity.EXTRA_GAME, game);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "Failed to join game", Toast.LENGTH_LONG).show();
            }
        });
    }
}
