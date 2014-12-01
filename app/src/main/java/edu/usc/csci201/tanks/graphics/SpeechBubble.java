package edu.usc.csci201.tanks.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.usc.csci201.tanks.GameActivity;
import edu.usc.csci201.tanks.PlayerInfo;

/**
 * Created by nickentin on 11/17/14.
 */
public class SpeechBubble extends ScreenObject {
    protected PlayerInfo player;
    protected String displayName;
    protected SpeechBubbleType type;

    protected boolean active = false;
    protected Paint inactivePaint = new Paint();
    protected Paint textPaint = new Paint();

    protected Context context;
    protected Bitmap image;

    public SpeechBubble(SpeechBubbleType type, PlayerInfo player, GameActivity activity) {
        this.player = player;
        this.type = type;
        this.context = activity;

        if (type == SpeechBubbleType.ALL) {
            this.displayName = "ALL";
            this.active = true;
        } else if (type == SpeechBubbleType.TEAM) {
            this.displayName = "TEAM";
        } else if (type == SpeechBubbleType.PLAYER) {
            String[] nameParts = player.getName().split(" ");
            this.displayName = nameParts[0].substring(0,1) + (nameParts.length > 1 ? nameParts[nameParts.length-1].substring(0,1) : "");
        }

        this.inactivePaint.setColor(Color.BLACK);
        this.inactivePaint.setStyle(Paint.Style.FILL);
        this.inactivePaint.setAlpha(75);
    }

    @Override
    public void setFrame(int x, int y, final int width, int height) {
        super.setFrame(x, y, width, height);
        if (this.type == SpeechBubbleType.PLAYER) {
            Log.i("SpeechBubble","Calling AsyncTask");
            new AsyncTask<Void,Void,Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... params) {
                    try {
                        return player.getImage(context,width);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    Log.i("SpeechBubble","Setting image");
                    SpeechBubble.this.image = result;
                    super.onPostExecute(result);
                }
            }.execute();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.image == null) {
            // draw text
            textPaint.setColor(Color.WHITE);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setTextSize(24.0f);
            canvas.drawText(this.displayName,this.frame.left+this.frame.width()/2,this.frame.top+this.frame.height()/2, textPaint);
        } else {
            // draw image
            canvas.drawBitmap(this.image,this.frame.left,this.frame.top,null);
        }

        // if inactive, make darker
        if (!active) {
            canvas.drawRect(this.frame.left, this.frame.top, this.frame.right, this.frame.bottom-1, inactivePaint);
        }
    }

    public void activate(ChatInterfaceListener chatDelegate) {
        this.active = true;
        if (this.type == SpeechBubbleType.ALL) {
            chatDelegate.userDidSelectChannel(this.player, ChatInterfaceListener.ChatChannel.ALL);
        } else if (this.type == SpeechBubbleType.TEAM) {
            chatDelegate.userDidSelectChannel(this.player, ChatInterfaceListener.ChatChannel.TEAM);
        } else {
            chatDelegate.userDidSelectChannel(this.player, ChatInterfaceListener.ChatChannel.USER);
        }
    }

    public enum SpeechBubbleType { ALL, TEAM, PLAYER }
}
