package edu.usc.csci201.tanks.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by nickentin on 11/17/14.
 */
public class Board extends ScreenObject {
    private int xtiles;
    private int ytiles;

    private Tile[][] grid;

    private Paint backgroundPaint = new Paint();

    public Board(int xtiles, int ytiles) {
        this.xtiles = xtiles;
        this.ytiles = ytiles;

        grid = new Tile[xtiles][ytiles];
        for (int i = 0 ; i < xtiles ; i++) {
            for (int j = 0 ; j < ytiles ; j++) {
                grid[i][j] = new Tile();
            }
        }

        this.backgroundPaint.setColor(Color.BLACK);
    }

    @Override
    public void setFrame(int x, int y, int width, int height) {
        super.setFrame(x, y, width, height);

        int padding_x = 0;
        int padding_y = 0;

        int size = 0;

        if ((float)xtiles/ytiles > (float)width/height) {
            size = width/xtiles;
            padding_y = (height-(size*ytiles))/2;
        } else {
            size = height/ytiles;
            padding_x = (width-(size*xtiles))/2;
        }

        for (int i = 0 ; i < xtiles ; i++) {
            for (int j = 0 ; j < ytiles ; j++) {
                grid[i][j].setFrame(this.frame.left+padding_x+size*i,this.frame.top+padding_y+size*j,size,size);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.frame,backgroundPaint);

        for (int i = 0 ; i < xtiles ; i++) {
            for (int j = 0 ; j < ytiles ; j++) {
                grid[i][j].draw(canvas);
            }
        }
    }
}
