package edu.usc.csci201.tanks;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import edu.usc.csci201.tanks.network.responses.Game;

/**
 * Created by vmagro on 11/23/14.
 */
public class GameActivity extends Activity {
    public static final String EXTRA_GAME = "edu.usc.csci201.tanks.GameActivity.EXTRA_GAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null && getIntent().getExtras() != null) {
            Game game = (Game) getIntent().getSerializableExtra(EXTRA_GAME);
            Toast.makeText(this, "Joining game: " + game.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
