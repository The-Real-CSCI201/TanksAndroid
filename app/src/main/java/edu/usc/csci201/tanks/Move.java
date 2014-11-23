package edu.usc.csci201.tanks;

/**
 * Created by vmagro on 11/23/14.
 */
public class Move {

    private final Type type;
    private final Direction direction;

    public static enum Type {
        MOVE, SHOOT
    }

    public static enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    public Move(Type type, Direction direction) {
        this.type = type;
        this.direction = direction;
    }

    public Type getType() {
        return type;
    }

    public Direction getDirection() {
        return direction;
    }

    public String toString() {
        return "Move[" + type.name() + "," + direction.name() + "]";
    }

}
