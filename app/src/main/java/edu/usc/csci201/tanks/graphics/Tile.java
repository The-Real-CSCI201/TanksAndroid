package edu.usc.csci201.tanks.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import edu.usc.csci201.tanks.R;

enum TileVisibility {VISIBLE, TERRAIN_ONLY, HIDDEN}

/**
 * Created by nickentin on 11/17/14.
 */
public class Tile extends ScreenObject {
    private TileVisibility visibility;
    private Paint borderPaint = new Paint();
    private Paint backgroundPaint = new Paint();
    private Bitmap backgroundImage;

    public Tile (boolean hasObstacle, Resources res) {
        if (hasObstacle) {
            backgroundImage = BitmapFactory.decodeResource(res, R.drawable.sand_obstacle);
        } else {
            backgroundImage = BitmapFactory.decodeResource(res, R.drawable.sand_empty);
        }
    }

    @Override
    public void setFrame(int x, int y, int width, int height) {
        super.setFrame(x, y, width, height);
        backgroundImage = Bitmap.createScaledBitmap(backgroundImage, width-1, width-1, false);
    }

    public TileVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(TileVisibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public void draw(Canvas canvas) {
        // draw background image
        canvas.drawBitmap(backgroundImage,this.frame.left,this.frame.top,backgroundPaint);

        // draw border
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(1.0f);
        borderPaint.setAlpha(50);
        canvas.drawRect(this.frame, borderPaint);
    }
}
