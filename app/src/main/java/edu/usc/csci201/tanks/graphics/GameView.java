package edu.usc.csci201.tanks.graphics;

import android.content.res.Resources;
import android.graphics.Canvas;

import edu.usc.csci201.tanks.GameActivity;
import edu.usc.csci201.tanks.GameState;
import edu.usc.csci201.tanks.PlayerInfo;
import edu.usc.csci201.tanks.gameplay.Game;

/**
 * Created by nickentin on 11/17/14.
 */
public class GameView extends ScreenObject implements GameState.PlayerAddedListener, Game.TurnListener {
    // interface components
    private Board board;
    private TopBar topBar;

    // Android Resources
    private Resources res;

    // gameplay delegate
    private GameplayInterfaceListener delegate;

    public GameView(GameActivity activity, Resources res, GameplayInterfaceListener delegate, ChatInterfaceListener chatListener) {
        this.res = res;
        this.delegate = delegate;

        this.board = new Board(delegate, res);
        this.topBar = new TopBar(delegate, chatListener, activity);
    }

    @Override
    public void setFrame(int x, int y, int width, int height) {
        super.setFrame(x, y, width, height);

        int topBarHeight = 150;

        if (topBar != null)
            topBar.setFrame(x, y, width, topBarHeight);
        if (board != null)
            board.setFrame(x, y + topBarHeight, width, height - topBarHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        topBar.draw(canvas);
        board.draw(canvas);
    }

    @Override
    public void takeTurn() {
        this.board.takeTurn();
    }

    public void dealWithTouch(float x, float y) {
        int xi = Math.round(x);
        int yi = Math.round(y);
        if (this.topBar.frame.contains(xi, yi)) {
            this.topBar.dealWithTouch(xi, yi);
        } else if (this.board.frame.contains(xi, yi)) {
            this.board.dealWithTouch(xi, yi);
        }
    }

    @Override
    public void playerAdded(PlayerInfo addedPlayer) {
        this.board.playerAdded(addedPlayer);
        this.topBar.playerAdded(addedPlayer);
    }
}
