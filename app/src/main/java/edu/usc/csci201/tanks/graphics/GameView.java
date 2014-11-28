package edu.usc.csci201.tanks.graphics;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nickentin on 11/17/14.
 */
public class GameView extends ScreenObject {
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
        this.topBar = new TopBar(delegate);

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
}
