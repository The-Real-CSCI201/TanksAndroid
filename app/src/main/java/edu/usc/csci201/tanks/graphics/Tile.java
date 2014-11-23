package edu.usc.csci201.tanks.graphics;

enum TileVisibility {VISIBLE, TERRAIN_ONLY, HIDDEN}

/**
 * Created by nickentin on 11/17/14.
 */
public class Tile {
    private TileVisibility visibility;

    public TileVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(TileVisibility visibility) {
        this.visibility = visibility;
    }
}
