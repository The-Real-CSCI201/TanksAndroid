package edu.usc.csci201.tanks.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

enum TileVisibility {VISIBLE, TERRAIN_ONLY, HIDDEN}

/**
 * Created by nickentin on 11/17/14.
 */
public class Tile extends ScreenObject {
    private TileVisibility visibility;
    private Paint paint = new Paint();

    public TileVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(TileVisibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public void draw(Canvas canvas) {
        // draw border
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1.0f);
        paint.setAlpha(50);
        canvas.drawRect(this.frame,paint);
    }
}
