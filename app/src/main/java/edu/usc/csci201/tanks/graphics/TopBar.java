package edu.usc.csci201.tanks.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by nickentin on 11/17/14.
 */
public class TopBar extends ScreenObject {
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        canvas.drawRect(this.frame,paint);
//        canvas.drawRect(0,0,10,10,paint);

//        System.out.println("frame: " + this.frame.top + ", " + this.frame.left + ", " + this.frame.width() + ", " + this.frame.height());
    }
}
