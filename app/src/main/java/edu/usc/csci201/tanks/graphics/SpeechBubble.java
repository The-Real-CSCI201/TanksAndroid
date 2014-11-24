package edu.usc.csci201.tanks.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by nickentin on 11/17/14.
 */
public class SpeechBubble extends ScreenObject {
    protected String name;
    protected String displayName;
    protected Paint paint = new Paint();

    public SpeechBubble(SpeechBubbleType type, String name) {
        this.name = name;
        this.displayName = name.substring(0,1);
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1.0f);
        canvas.drawRect(this.frame,paint);

        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(14.0f);
        canvas.drawText(this.displayName,this.frame.left+this.frame.width()/2,this.frame.top+this.frame.height()/2,paint);
    }

    public enum SpeechBubbleType { ALL, TEAM, PLAYER }
}
