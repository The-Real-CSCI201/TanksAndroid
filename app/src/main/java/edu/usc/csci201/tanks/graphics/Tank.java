package edu.usc.csci201.tanks.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import edu.usc.csci201.tanks.R;
import edu.usc.csci201.tanks.common.TankType;

/**
 * Created by nickentin on 11/17/14.
 */
public class Tank extends ScreenObject {
    public boolean isMoving = false;
    private int color;
    private Bitmap sprite[] = new Bitmap[2];
    private int sprite_frame = 0;

    public Tank(TankType type, Resources res) {
        switch (type) {
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
        sprite[0] = Bitmap.createScaledBitmap(sprite[0], this.frame.width(), this.frame.height(), false);
        sprite[1] = Bitmap.createScaledBitmap(sprite[1], this.frame.width(), this.frame.height(), false);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(sprite[sprite_frame], this.frame.left, this.frame.top, null);
        sprite_frame = (sprite_frame + 1) % 2;
    }
}
