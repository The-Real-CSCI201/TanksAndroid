package edu.usc.csci201.tanks.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import edu.usc.csci201.tanks.R;
import edu.usc.csci201.tanks.common.TankType;
import edu.usc.csci201.tanks.gameplay.Player;

/**
 * Created by nickentin on 11/17/14.
 */
public class Tank extends ScreenObject {
    public boolean isMoving = false;
    private int color;
    private Bitmap sprite[] = new Bitmap[2];
    private int sprite_frame = 0;

    private Player player;

    private int offset_x;
    private int offset_y;
    private int box_size;
    private Paint healthBarPaint = new Paint();

    public Tank(Player player, Resources res) {
        this.player = player;

        switch (player.getTankType()) {
            case USER:
                sprite[0] = BitmapFactory.decodeResource(res, R.drawable.tank_user0);
                sprite[1] = BitmapFactory.decodeResource(res, R.drawable.tank_user1);
                break;
            case TEAM:
                sprite[0] = BitmapFactory.decodeResource(res, R.drawable.tank_team0);
                sprite[1] = BitmapFactory.decodeResource(res, R.drawable.tank_team1);
                break;
            case OPPONENT:
                sprite[0] = BitmapFactory.decodeResource(res, R.drawable.tank_opponent0);
                sprite[1] = BitmapFactory.decodeResource(res, R.drawable.tank_opponent1);
        }
        System.out.println("Sprites created");
    }

    public void setDrawParameters(int offset_x, int offset_y, int box_size) {
        this.offset_x = offset_x;
        this.offset_y = offset_y;
        this.box_size = box_size;

        sprite[0] = Bitmap.createScaledBitmap(sprite[0], box_size-1, box_size-1, false);
        sprite[1] = Bitmap.createScaledBitmap(sprite[1], box_size-1, box_size-1, false);
    }

    private int GREEN_MIN_HEALTH = Player.MAX_HEALTH/2;
    private int YELLOW_MIN_HEALTH = Player.MAX_HEALTH/4;

    @Override
    public void draw(Canvas canvas) {
        int left = this.offset_x + this.box_size*player.getCol() + 1;
        int top = this.offset_y + this.box_size*player.getRow() + 1;

        canvas.drawBitmap(sprite[sprite_frame], left, top, null);
        if (isMoving) {
            sprite_frame = (sprite_frame + 1) % 2;
        }

        int bar_width = this.box_size-20;
        bar_width *= ((double)player.getHealth() / Player.MAX_HEALTH);
        if (player.getHealth() > GREEN_MIN_HEALTH) {
            healthBarPaint.setColor(Color.GREEN);
        } else if (player.getHealth() > YELLOW_MIN_HEALTH) {
            healthBarPaint.setColor(Color.YELLOW);
        } else {
            healthBarPaint.setColor(Color.RED);
        }

        canvas.drawRect(left+9,top+box_size-15,left+9+bar_width,top+box_size-9,healthBarPaint);
    }
}
