package edu.usc.csci201.tanks.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import edu.usc.csci201.tanks.PlayerInfo;
import edu.usc.csci201.tanks.R;
import edu.usc.csci201.tanks.common.Direction;
import edu.usc.csci201.tanks.common.TankType;

/**
 * Created by nickentin on 11/17/14.
 */
public class Tank extends ScreenObject {
    public boolean isMoving = false;
    private int color;
    private Bitmap sprite[] = new Bitmap[2];
    private int sprite_frame = 0;
    private Matrix matrix = new Matrix();

    private PlayerInfo player;

    private int offset_x;
    private int offset_y;
    private int box_size;
    private Paint healthBarPaint = new Paint();

    public Tank(PlayerInfo player, Resources res) {
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

    private int GREEN_MIN_HEALTH = PlayerInfo.MAX_HEALTH/2;
    private int YELLOW_MIN_HEALTH = PlayerInfo.MAX_HEALTH/4;

    @Override
    public void draw(Canvas canvas) {
        int left = this.offset_x + this.box_size*player.getLocation().x + 1;
        int top = this.offset_y + this.box_size*player.getLocation().y + 1;

        // attempt to rotate based on orientation
//        matrix.reset();
//        matrix.setTranslate(left, top);
//        matrix.postRotate(directionToDegrees(Direction.EAST),this.box_size/2,this.box_size/2);
//        canvas.drawBitmap(sprite[sprite_frame], matrix, null);

        canvas.drawBitmap(sprite[sprite_frame], left, top, null);
        if (isMoving) {
            sprite_frame = (sprite_frame + 1) % 2;
        }

        int bar_width = this.box_size-20;
        bar_width *= ((double)player.getHealth() / PlayerInfo.MAX_HEALTH);
        if (player.getHealth() > GREEN_MIN_HEALTH) {
            healthBarPaint.setColor(Color.GREEN);
        } else if (player.getHealth() > YELLOW_MIN_HEALTH) {
            healthBarPaint.setColor(Color.YELLOW);
        } else {
            healthBarPaint.setColor(Color.RED);
        }

        canvas.drawRect(left+9,top+box_size-15,left+9+bar_width,top+box_size-9,healthBarPaint);
    }

    private float directionToDegrees(Direction dir) {
        switch (dir) {
            case NORTH:
                return 270.0f;
            case EAST:
                return 0.0f;
            case SOUTH:
                return 90.0f;
            case WEST:
                return 180.0f;
        }
        return 0.0f;
    }
}
