package edu.usc.csci201.tanks.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by nickentin on 11/19/14.
 */
public abstract class ScreenObject {
    protected Rect frame;

    public void setFrame (int x, int y, int width, int height) {
        this.frame = new Rect(x,y,x+width,y+height);
    }

    public abstract void draw (Canvas canvas);
}
