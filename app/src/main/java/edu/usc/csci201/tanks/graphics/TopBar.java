package edu.usc.csci201.tanks.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import edu.usc.csci201.tanks.GameActivity;
import edu.usc.csci201.tanks.PlayerInfo;

/**
 * Created by nickentin on 11/17/14.
 */
public class TopBar extends ScreenObject {
    private GameplayInterfaceListener gameDelegate;
    private ChatInterfaceListener chatDelegate;
    private GameActivity activity;
    private ArrayList<SpeechBubble> bubbles;

    private Paint textPaint = new Paint();
    private Paint btnPaint = new Paint();
    private int backgroundColor = Color.DKGRAY;

    public TopBar(GameplayInterfaceListener gameDelegate, ChatInterfaceListener chatDelegate, GameActivity activity) {
        this.gameDelegate = gameDelegate;
        this.chatDelegate = chatDelegate;
        this.activity = activity;
        List<PlayerInfo> players = gameDelegate.getPlayers();
        for (int i = 0 ; i < players.size() ; i++) {
            if (players.get(i).isMe()) {
                players.remove(i);
            }
        }

        bubbles = new ArrayList<SpeechBubble>(players.size()+2);
        bubbles.add(new SpeechBubble(SpeechBubble.SpeechBubbleType.ALL, null, activity));
        bubbles.add(new SpeechBubble(SpeechBubble.SpeechBubbleType.TEAM, null, activity));

        for (int i = 0 ; i < players.size() ; i++) {
            bubbles.add(new SpeechBubble(SpeechBubble.SpeechBubbleType.PLAYER, players.get(i), activity));
        }

        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);
        btnPaint.setColor(Color.GRAY);
    }

    @Override
    public void setFrame(int x, int y, int width, int height) {
        super.setFrame(x, y, width, height);

        for (int i = 0 ; i < bubbles.size() ; i++) {
            if (this.frame != null)
                bubbles.get(i).setFrame((x + width) - height * (bubbles.size() - i), this.frame.top, height, height);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(backgroundColor);

        // draw menu button
//        int buttonWidth = this.frame.height();
//        canvas.drawRect(this.frame.left,this.frame.top,this.frame.left+buttonWidth,this.frame.bottom-2,btnPaint);
//        canvas.drawText("MENU",this.frame.left+(buttonWidth/2),this.frame.top+this.frame.height()/2,textPaint);

        // draw speech bubbles
        for (int i = 0 ; i < bubbles.size() ; i++) {
            bubbles.get(i).draw(canvas);
        }
    }

    public void dealWithTouch(int x, int y) {
        // check if touch is within menu button
        if (x < this.frame.height()) {
            // TODO: display menu
            System.out.println("Touched menu button");
        }

        // check if touch is within speech bubble
        for (int i = 0 ; i < bubbles.size() ; i++) {
            if (bubbles.get(i).frame.contains(x, y)) {
                for (int j = 0 ; j < i ; j++) {
                    bubbles.get(j).active = false;
                }
                for (int j = i+1 ; j < bubbles.size() ; j++) {
                    bubbles.get(j).active = false;
                }
                bubbles.get(i).activate(chatDelegate);
                return;
            }
        }
    }


    public void playerAdded(PlayerInfo addedPlayer) {
        bubbles.add(new SpeechBubble(SpeechBubble.SpeechBubbleType.PLAYER, addedPlayer, activity));
        this.setFrame(this.frame.left,this.frame.top,this.frame.width(),this.frame.height());
    }
}
