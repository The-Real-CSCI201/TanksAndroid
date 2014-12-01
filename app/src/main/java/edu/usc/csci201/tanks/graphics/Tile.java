package edu.usc.csci201.tanks.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

enum TileVisibility {VISIBLE, TERRAIN_ONLY, HIDDEN}

/**
 * Created by nickentin on 11/17/14.
 */
public class Tile extends ScreenObject {
    private TileVisibility visibility;
    private Paint borderPaint = new Paint();
    private Paint backgroundPaint = new Paint();
    public Bitmap backgroundImage = null;

    public Tile() {
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
        if (backgroundImage != null) {
            canvas.drawBitmap(backgroundImage, this.frame.left, this.frame.top, backgroundPaint);
        }

        // draw border
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(1.0f);
        borderPaint.setAlpha(50);
        canvas.drawRect(this.frame, borderPaint);
    }
}
