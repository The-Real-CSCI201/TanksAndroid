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
    protected SpeechBubbleType type;

    protected boolean active = false;
    protected Paint inactivePaint = new Paint();
    protected Paint textPaint = new Paint();

    public SpeechBubble(SpeechBubbleType type, String name) {
        this.name = name;
        this.type = type;

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
        // draw border
//        textPaint.setColor(Color.WHITE);
//        textPaint.setStyle(Paint.Style.STROKE);
//        textPaint.setStrokeWidth(1.0f);
//        canvas.drawRect(this.frame.left, this.frame.top, this.frame.right-1, this.frame.bottom-1, textPaint);

        // draw text
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(24.0f);
        canvas.drawText(this.displayName,this.frame.left+this.frame.width()/2,this.frame.top+this.frame.height()/2, textPaint);

        // if inactive, make darker
        if (!active) {
            canvas.drawRect(this.frame.left, this.frame.top, this.frame.right, this.frame.bottom-1, inactivePaint);
        }
    }

    public void activate(ChatInterfaceListener chatDelegate) {
        this.active = true;
        if (this.type == SpeechBubbleType.ALL) {
            chatDelegate.userDidSelectChannel(ChatInterfaceListener.ChatChannel.ALL, this.name);
        } else if (this.type == SpeechBubbleType.TEAM) {
            chatDelegate.userDidSelectChannel(ChatInterfaceListener.ChatChannel.TEAM, this.name);
        } else {
            chatDelegate.userDidSelectChannel(ChatInterfaceListener.ChatChannel.USER, this.name);
        }
    }

    public enum SpeechBubbleType { ALL, TEAM, PLAYER }
}
