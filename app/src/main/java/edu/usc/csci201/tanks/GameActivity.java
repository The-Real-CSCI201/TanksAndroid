package edu.usc.csci201.tanks;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

import edu.usc.csci201.tanks.common.Direction;
import edu.usc.csci201.tanks.graphics.GameView;
import edu.usc.csci201.tanks.graphics.GameplayInterfaceListener;

public class GameActivity extends Activity implements SurfaceHolder.Callback {
    public static final String EXTRA_GAME = "edu.usc.csci201.tanks.GameActivity.EXTRA_GAME";

    protected SurfaceView surfaceView = null;
    protected GameView tanksView = null;
    protected Timer graphicsTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.surfaceView = (SurfaceView)findViewById(R.id.surface);
        this.surfaceView.getHolder().addCallback(this);

        // DEBUG: sample gameplay interface listener
        this.tanksView = new GameView(Resources.getSystem(), new GameplayInterfaceListener() {
            @Override
            public void userDidPauseGame() {
                System.out.println("User did pause game");
            }

            @Override
            public void userDidResumeGame() {
                System.out.println("User did resume game");
            }

            @Override
            public void userDidQuitGame() {
                System.out.println("Use did quit game");
            }

            @Override
            public int mapWidth() {
                return 14;
            }

            @Override
            public int mapHeight() {
                return 7;
            }

            @Override
            public boolean tileHasNorthWall(int row, int col) {
                return false;
            }

            @Override
            public boolean tileHasEastWall(int row, int col) {
                return false;
            }

            @Override
            public boolean tileHasSouthWall(int row, int col) {
                return false;
            }

            @Override
            public boolean tileHasWestWall(int row, int col) {
                return false;
            }

            @Override
            public boolean userCanMoveInDirection(Direction direction) {
                return true;
            }

            @Override
            public void userDidMoveInDirection(Direction direction) {
                System.out.println("User did perform move");
            }

            @Override
            public boolean userDidFireInDirection(Direction direction) {
                System.out.println("User did perform fire");
                return true;
            }

            @Override
            public int timeRemainingInCurrentTurn() {
                return 83;
            }

            @Override
            public int numberOfPlayers() {
                return 4;
            }

            @Override
            public String[] getPlayerNames() {
                return new String[]{"Self","Mate","Other","Person"};
            }
        });
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
                }
            }
        },0,25); // 25 -> 40 fps
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int frmt, int w, int h) {
        tanksView.setFrame(surfaceView.getLeft(),surfaceView.getTop(),surfaceView.getWidth(),surfaceView.getHeight());
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
}
