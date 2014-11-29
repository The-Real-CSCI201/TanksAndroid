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
    protected Paint textPaint = new Paint();

    protected boolean active = false;
    protected Paint inactivePaint = new Paint();

    public SpeechBubble(SpeechBubbleType type, String name) {
        this.name = name;

        if (type == SpeechBubbleType.ALL) {
            this.displayName = "ALL";
            this.active = true;
        } else if (type == SpeechBubbleType.TEAM) {
            this.displayName = "TEAM";
        } else if (type == SpeechBubbleType.PLAYER) {
            String[] nameParts = name.split(" ");
            this.displayName = nameParts[0].substring(0,1) + (nameParts.length > 1 ? nameParts[nameParts.length-1].substring(0,1) : "");
        }

        this.inactivePaint.setColor(Color.BLACK);
        this.inactivePaint.setStyle(Paint.Style.FILL);
        this.inactivePaint.setAlpha(75);
    }

    @Override
    public void draw(Canvas canvas) {
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(1.0f);
        canvas.drawRect(this.frame.left, this.frame.top, this.frame.right-1, this.frame.bottom-1, textPaint);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(24.0f);
        canvas.drawText(this.displayName,this.frame.left+this.frame.width()/2,this.frame.top+this.frame.height()/2, textPaint);

        if (!active) {
            canvas.drawRect(this.frame.left, this.frame.top, this.frame.right, this.frame.bottom-1, inactivePaint);
        }
    }

    public enum SpeechBubbleType { ALL, TEAM, PLAYER }
}
