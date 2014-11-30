package edu.usc.csci201.tanks.graphics;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Timer;
import java.util.TimerTask;

import edu.usc.csci201.tanks.GameState;
import edu.usc.csci201.tanks.PlayerInfo;

/**
 * Created by nickentin on 11/17/14.
 */
public class GameView extends ScreenObject implements GameState.PlayerAddedListener {
    // interface components
    private Board board;
    private TopBar topBar;

    // Android Resources
    private Resources res;
    private Paint paint;

    // gameplay delegate
    private GameplayInterfaceListener delegate;

    public GameView(Resources res, GameplayInterfaceListener delegate, ChatInterfaceListener chatListener) {
        this.res = res;
        this.delegate = delegate;

        this.board = new Board(delegate,res);
        this.topBar = new TopBar(delegate, chatListener);

        this.paint = new Paint();
        this.paint.setColor(Color.RED);
    }

    @Override
    public void setFrame(int x, int y, int width, int height) {
        super.setFrame(x,y,width,height);

        int topBarHeight = 150;

        if (topBar != null)
            topBar.setFrame(x,y,width,topBarHeight);
        if (board != null)
            board.setFrame(x, y + topBarHeight, width, height - topBarHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        topBar.draw(canvas);
        board.draw(canvas);
    }

    public void takeTurn() {
        this.board.takeTurn();
    }

    public void dealWithTouch(float x, float y) {
        int xi = Math.round(x);
        int yi = Math.round(y);
        if (this.topBar.frame.contains(xi,yi)) {
            this.topBar.dealWithTouch(xi,yi);
        } else if (this.board.frame.contains(xi,yi)) {
            this.board.dealWithTouch(xi,yi);
        }
    }

    @Override
    public void playerAdded(PlayerInfo addedPlayer) {
        this.board.playerAdded(addedPlayer);
        this.topBar.playerAdded(addedPlayer);
    }
}
