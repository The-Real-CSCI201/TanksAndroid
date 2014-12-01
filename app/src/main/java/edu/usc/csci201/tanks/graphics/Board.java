package edu.usc.csci201.tanks.graphics;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import edu.usc.csci201.tanks.GameState;
import edu.usc.csci201.tanks.PlayerInfo;
import edu.usc.csci201.tanks.common.Direction;
import edu.usc.csci201.tanks.common.TankType;

/**
 * Created by nickentin on 11/17/14.
 */
public class Board extends ScreenObject {
    private int xtiles;
    private int ytiles;

    private boolean waitingForAction = false;
    private boolean currentActionIsMove = true;

    private Tile[][] grid;
    private ArrayList<Tank> tanks;
    private String curPlayerId;

    private Paint movePaint = new Paint();
    private Paint shootPaint = new Paint();
    private Paint backgroundPaint = new Paint();

    // gameplay delegate
    private GameplayInterfaceListener delegate;

    private Resources res;

    public Board(GameplayInterfaceListener delegate, Resources res) {
        this.res = res;
        this.delegate = delegate;

        this.xtiles = delegate.mapWidth();
        this.ytiles = delegate.mapHeight();

        grid = new Tile[xtiles][ytiles];
        for (int i = 0 ; i < xtiles ; i++) {
            for (int j = 0 ; j < ytiles ; j++) {
                grid[i][j] = new Tile(delegate.tileHasObstacle(j,i),res);
            }
        }

        this.backgroundPaint.setColor(Color.BLACK);
        this.movePaint.setColor(Color.CYAN);
        this.movePaint.setAlpha(50);
        this.shootPaint.setColor(Color.MAGENTA);
        this.shootPaint.setAlpha(50);

        List<PlayerInfo> players = delegate.getPlayers();
        this.tanks = new ArrayList<Tank>(players.size());
        for (int i = 0 ; i < players.size() ; i++) {
            this.tanks.add(new Tank(players.get(i).getId(),res));
            if (players.get(i).getTankType() == TankType.USER) {
                curPlayerId = players.get(i).getId();
            }
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

        for (int i = 0 ; i < tanks.size() ; i++) {
            tanks.get(i).setDrawParameters(this.frame.left+padding_x,this.frame.top+padding_y,size);
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

        for (int i = 0 ; i < tanks.size() ; i++) {
            tanks.get(i).draw(canvas);
        }

        // draw turn options
        if (waitingForAction) {
            // draw switch on current cell
            canvas.drawRect(grid[GameState.getInstance().getPlayer(curPlayerId).getLocation().x][GameState.getInstance().getPlayer(curPlayerId).getLocation().y].frame,(currentActionIsMove ? shootPaint : movePaint));

            // draw up cell
            if (GameState.getInstance().getPlayer(curPlayerId).getLocation().y > 0) {
                canvas.drawRect(grid[GameState.getInstance().getPlayer(curPlayerId).getLocation().x][GameState.getInstance().getPlayer(curPlayerId).getLocation().y-1].frame,(currentActionIsMove ? movePaint : shootPaint));
            }

            // draw down cell
            if (GameState.getInstance().getPlayer(curPlayerId).getLocation().y < delegate.mapHeight()-1) {
                canvas.drawRect(grid[GameState.getInstance().getPlayer(curPlayerId).getLocation().x][GameState.getInstance().getPlayer(curPlayerId).getLocation().y+1].frame,(currentActionIsMove ? movePaint : shootPaint));
            }

            // draw left cell
            if (GameState.getInstance().getPlayer(curPlayerId).getLocation().x > 0) {
                canvas.drawRect(grid[GameState.getInstance().getPlayer(curPlayerId).getLocation().x-1][GameState.getInstance().getPlayer(curPlayerId).getLocation().y].frame,(currentActionIsMove ? movePaint : shootPaint));
            }

            // draw right cell
            if (GameState.getInstance().getPlayer(curPlayerId).getLocation().x < delegate.mapWidth()-1) {
                canvas.drawRect(grid[GameState.getInstance().getPlayer(curPlayerId).getLocation().x+1][GameState.getInstance().getPlayer(curPlayerId).getLocation().y].frame,(currentActionIsMove ? movePaint : shootPaint));
            }
        }
    }

    public void takeTurn() {
        this.waitingForAction = true;
        this.currentActionIsMove = true;
    }

    public void dealWithTouch(int x, int y) {
        if (waitingForAction) {
            // if center cell, switch mode
            if (grid[GameState.getInstance().getPlayer(curPlayerId).getLocation().x][GameState.getInstance().getPlayer(curPlayerId).getLocation().y].frame.contains(x,y)) {
                this.currentActionIsMove = !this.currentActionIsMove;
            }
            // if direction up, alert delegate to decision
            else if (GameState.getInstance().getPlayer(curPlayerId).getLocation().y > 0 && grid[GameState.getInstance().getPlayer(curPlayerId).getLocation().x][GameState.getInstance().getPlayer(curPlayerId).getLocation().y-1].frame.contains(x,y)) {
                if (this.currentActionIsMove) {
                    System.out.println("Moving up");
                    delegate.userDidMoveInDirection(Direction.NORTH);
                } else {
                    System.out.println("Shooting up");
                    delegate.userDidFireInDirection(Direction.NORTH);
                }
                this.waitingForAction = false;
            }
            // if direction down, alert delegate to decision
            else if (GameState.getInstance().getPlayer(curPlayerId).getLocation().y < delegate.mapHeight()-1 && grid[GameState.getInstance().getPlayer(curPlayerId).getLocation().x][GameState.getInstance().getPlayer(curPlayerId).getLocation().y+1].frame.contains(x,y)) {
                if (this.currentActionIsMove) {
                    System.out.println("Moving down");
                    delegate.userDidMoveInDirection(Direction.SOUTH);
                } else {
                    System.out.println("Shooting down");
                    delegate.userDidFireInDirection(Direction.SOUTH);
                }
                this.waitingForAction = false;
            }
            // if direction left, alert delegate to decision
            else if (GameState.getInstance().getPlayer(curPlayerId).getLocation().x > 0 && grid[GameState.getInstance().getPlayer(curPlayerId).getLocation().x-1][GameState.getInstance().getPlayer(curPlayerId).getLocation().y].frame.contains(x,y)) {
                if (this.currentActionIsMove) {
                    System.out.println("Moving left");
                    delegate.userDidMoveInDirection(Direction.WEST);
                } else {
                    System.out.println("Shooting left");
                    delegate.userDidFireInDirection(Direction.WEST);
                }
                this.waitingForAction = false;
            }
            // if direction right, alert delegate to decision
            else if (GameState.getInstance().getPlayer(curPlayerId).getLocation().x < delegate.mapWidth()-1 && grid[GameState.getInstance().getPlayer(curPlayerId).getLocation().x+1][GameState.getInstance().getPlayer(curPlayerId).getLocation().y].frame.contains(x,y)) {
                if (this.currentActionIsMove) {
                    System.out.println("Moving right");
                    delegate.userDidMoveInDirection(Direction.EAST);
                } else {
                    System.out.println("Shooting right");
                    delegate.userDidFireInDirection(Direction.EAST);
                }
                this.waitingForAction = false;
            }
        }
    }

    public void playerAdded(PlayerInfo addedPlayer) {
        this.tanks.add(new Tank(addedPlayer.getId(), this.res));
        this.setFrame(this.frame.left, this.frame.top, this.frame.width(), this.frame.height());
    }
}
