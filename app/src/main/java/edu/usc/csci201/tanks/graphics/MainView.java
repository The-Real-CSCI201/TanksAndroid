package edu.usc.csci201.tanks.graphics;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nickentin on 11/17/14.
 */
public class MainView {
    // interface components
    private Board board = new Board();
    private TopBar topBar = new TopBar();

    // graphics objects
    private Canvas canvas;

    // timer to redraw graphics
    private Timer refreshTimer;

    // Android Resources
    private Resources res;

    // frame rate (fps)
    private int frame_rate = 30;

    public MainView (Resources res) {
        this.res = res;
    }

    public void setFrame(int x, int y, int width, int height) {
        int topBarHeight = height/5;

        topBar.setFrame(x,y,width,topBarHeight);
        board.setFrame(x,y+topBarHeight,width,height-topBarHeight);
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void startDrawing() {
        if (refreshTimer != null) {
            refreshTimer.cancel();
            refreshTimer = new Timer();
        } else {
            refreshTimer = new Timer();
        }

        refreshTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                drawChildren();
            }
        },0,1000/frame_rate);
    }

    public void stopDrawing() {
        refreshTimer.cancel();
    }

    protected void drawChildren() {
        topBar.draw(canvas);
        board.draw(canvas);
    }
}
