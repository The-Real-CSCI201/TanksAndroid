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
public class MainView extends ScreenObject {
    // interface components
    private Board board = new Board();
    private TopBar topBar = new TopBar();

    // Android Resources
    private Resources res;
    private Paint paint;

    public MainView (Resources res) {
        this.res = res;
        this.paint.setColor(Color.RED);
    }

    @Override
    public void setFrame(int x, int y, int width, int height) {
        super.setFrame(x,y,width,height);

        int topBarHeight = height/5;

        topBar.setFrame(x,y,width,topBarHeight);
        board.setFrame(x, y + topBarHeight, width, height - topBarHeight);
    }

    @Override
    public void draw(Canvas canvas) {
//        topBar.draw(canvas);
//        board.draw(canvas);

        if (canvas == null) {
            System.out.println("Canvas is null");
        }

        canvas.drawRect(10,10,20,20,this.paint);
    }
}
