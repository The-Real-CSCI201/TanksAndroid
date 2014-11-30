package edu.usc.csci201.tanks.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by nickentin on 11/17/14.
 */
public class TopBar extends ScreenObject {
    private GameplayInterfaceListener gameDelegate;
    private ChatInterfaceListener chatDelegate;
    private SpeechBubble[] bubbles;

    private Paint textPaint = new Paint();
    private Paint btnPaint = new Paint();
    private int backgroundColor = Color.DKGRAY;

    public TopBar(GameplayInterfaceListener gameDelegate, ChatInterfaceListener chatDelegate) {
        this.gameDelegate = gameDelegate;
        this.chatDelegate = chatDelegate;
        String[] playerNames = gameDelegate.getPlayerNames();

        bubbles = new SpeechBubble[playerNames.length+1];

        bubbles[0] = new SpeechBubble(SpeechBubble.SpeechBubbleType.ALL, "ALL");
        bubbles[1] = new SpeechBubble(SpeechBubble.SpeechBubbleType.TEAM, "TEAM");

        for (int i = 1 ; i < playerNames.length ; i++) {
            bubbles[i+1] = new SpeechBubble(SpeechBubble.SpeechBubbleType.PLAYER, playerNames[i]);
        }

        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);
        btnPaint.setColor(Color.RED);
    }

    @Override
    public void setFrame(int x, int y, int width, int height) {
        super.setFrame(x, y, width, height);

        for (int i = 0 ; i < bubbles.length ; i++) {
            if (this.frame != null)
                bubbles[i].setFrame((x+width) - height*(bubbles.length-i), this.frame.top, height, height);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(backgroundColor);

        // menu button
        int buttonWidth = this.frame.height();
        canvas.drawRect(this.frame.left,this.frame.top,this.frame.left+buttonWidth,this.frame.bottom,btnPaint);
        canvas.drawText("MENU",this.frame.left+(buttonWidth/2),this.frame.top+this.frame.height()/2,textPaint);

        // countdown timer
        canvas.drawText(""+ gameDelegate.timeRemainingInCurrentTurn(),(int)(this.frame.left+buttonWidth*1.5),this.frame.top+this.frame.height()/2,textPaint);

        for (int i = 0 ; i < bubbles.length ; i++) {
            bubbles[i].draw(canvas);
        }
    }

    public void dealWithTouch(int x, int y) {
        // TODO: check if touch is within menu button

        // check if touch is within speech bubble
        for (int i = 0 ; i < bubbles.length ; i++) {
            if (bubbles[i].frame.contains(x,y)) {
                for (int j = 0 ; j < i ; j++) {
                    bubbles[j].active = false;
                }
                for (int j = i+1 ; j < bubbles.length ; j++) {
                    bubbles[j].active = false;
                }
                bubbles[i].activate(chatDelegate);
                return;
            }
        }
    }
}
