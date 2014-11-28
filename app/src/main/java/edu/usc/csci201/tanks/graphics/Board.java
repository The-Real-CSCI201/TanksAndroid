package edu.usc.csci201.tanks.graphics;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import edu.usc.csci201.tanks.common.TankType;
import edu.usc.csci201.tanks.gameplay.Player;

/**
 * Created by nickentin on 11/17/14.
 */
public class Board extends ScreenObject {
    private int xtiles;
    private int ytiles;

    private Tile[][] grid;
    private Tank[] tanks;

    private Paint backgroundPaint = new Paint();

    // gameplay delegate
    private GameplayInterfaceListener delegate;

    public Board(GameplayInterfaceListener delegate, Resources res) {
        this.delegate = delegate;

        this.xtiles = delegate.mapWidth();
        this.ytiles = delegate.mapHeight();

        grid = new Tile[xtiles][ytiles];
        for (int i = 0 ; i < xtiles ; i++) {
            for (int j = 0 ; j < ytiles ; j++) {
                grid[i][j] = new Tile();
            }
        }

        this.backgroundPaint.setColor(Color.BLACK);

        Player[] players = delegate.getPlayers();
        this.tanks = new Tank[players.length];
        for (int i = 0 ; i < players.length ; i++) {
            this.tanks[i] = new Tank(players[i],res);
        }
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

        for (int i = 0 ; i < tanks.length ; i++) {
            tanks[i].setDrawParameters(this.frame.left+padding_x,this.frame.top+padding_y,size);
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

        for (int i = 0 ; i < tanks.length ; i++) {
            tanks[i].draw(canvas);
        }
    }

}
