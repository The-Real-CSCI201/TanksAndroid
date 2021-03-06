package edu.usc.csci201.tanks;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

import edu.usc.csci201.tanks.chat.ChatListener;
import edu.usc.csci201.tanks.gameplay.Game;
import edu.usc.csci201.tanks.graphics.GameView;

public class GameActivity extends Activity implements SurfaceHolder.Callback {
    public static final String EXTRA_GAME = "edu.usc.csci201.tanks.GameActivity.EXTRA_GAME";

    protected SurfaceView surfaceView = null;
    protected GameView tanksView = null;
    protected Timer graphicsTimer = null;
    protected Game game;

    private Timer statsLaunchTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getActionBar().hide();

        game = new Game();
        ChatListener chatListener = new ChatListener(this);

        this.surfaceView = (SurfaceView) findViewById(R.id.surface);
        this.surfaceView.getHolder().addCallback(this);

        // set up game view
        this.tanksView = new GameView(this, getResources(), game, chatListener);
        GameState.getInstance().setPlayerAddedListener(this.tanksView);
        game.turnListener = this.tanksView;

        // start active
        this.tanksView.takeTurn();
    }

    public void surfaceCreated(final SurfaceHolder holder) {
        if (tanksView != null) {
            tanksView.setFrame(surfaceView.getLeft(), surfaceView.getTop(), surfaceView.getWidth(), surfaceView.getHeight());
        }

        graphicsTimer = new Timer();
        graphicsTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (tanksView != null) {
                    Canvas canvas = holder.lockCanvas();
                    if (canvas != null) {
                        tanksView.draw(canvas);
                        holder.unlockCanvasAndPost(canvas);
                    }
                    //check that game is finished
                    if (game.gameIsFinished()) {
                        graphicsTimer.cancel();
                        tanksView.gameOver();

                        statsLaunchTimer = new Timer();
                        statsLaunchTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                startActivity(Statistics.getInstance().getIntent(GameActivity.this));
                            }
                        }, 2000);
                    }
                }
            }
        }, 0, 50);
        // 16 ms -> 60 fps
        // 25 ms -> 40 fps
        // 50 ms -> 20 fps
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (statsLaunchTimer != null) {
            statsLaunchTimer.cancel();
            statsLaunchTimer = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int frmt, int w, int h) {
        tanksView.setFrame(surfaceView.getLeft(), surfaceView.getTop(), surfaceView.getWidth(), surfaceView.getHeight());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        graphicsTimer.cancel();
        graphicsTimer = null;
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

    private int statusBarHeight = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (statusBarHeight == 0) statusBarHeight = getStatusBarHeight();

        if (event.getAction() == MotionEvent.ACTION_UP) {
            this.tanksView.dealWithTouch(event.getX(), event.getY() - statusBarHeight);
        }

        return super.onTouchEvent(event);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
