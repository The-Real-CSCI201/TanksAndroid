package edu.usc.csci201.tanks.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import edu.usc.csci201.tanks.PlayerInfo;

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
        List<PlayerInfo> players = gameDelegate.getPlayers();
        for (int i = 0 ; i < players.size() ; i++) {
            if (players.get(i).isMe()) {
                players.remove(i);
            }
        }

        bubbles = new SpeechBubble[players.size()+2];
        bubbles[0] = new SpeechBubble(SpeechBubble.SpeechBubbleType.ALL, null);
        bubbles[1] = new SpeechBubble(SpeechBubble.SpeechBubbleType.TEAM, null);

        for (int i = 0 ; i < players.size() ; i++) {
            bubbles[i+2] = new SpeechBubble(SpeechBubble.SpeechBubbleType.PLAYER, players.get(i));
        }

        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);
        btnPaint.setColor(Color.GRAY);
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

        // draw menu button
        int buttonWidth = this.frame.height();
        canvas.drawRect(this.frame.left,this.frame.top,this.frame.left+buttonWidth,this.frame.bottom-2,btnPaint);
        canvas.drawText("MENU",this.frame.left+(buttonWidth/2),this.frame.top+this.frame.height()/2,textPaint);

        // draw speech bubbles
        for (int i = 0 ; i < bubbles.length ; i++) {
            bubbles[i].draw(canvas);
        }
    }

    public void dealWithTouch(int x, int y) {
        // check if touch is within menu button
        if (x < this.frame.height()) {
            // TODO: display menu
            System.out.println("Touched menu button");
        }

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
