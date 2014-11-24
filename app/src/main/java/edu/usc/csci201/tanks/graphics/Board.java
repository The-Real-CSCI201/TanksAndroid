package edu.usc.csci201.tanks.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by nickentin on 11/17/14.
 */
public class Board extends ScreenObject {

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        canvas.drawRect(this.frame,paint);
    }
}
