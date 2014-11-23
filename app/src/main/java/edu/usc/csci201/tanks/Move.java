package edu.usc.csci201.tanks;

import com.google.gson.annotations.Expose;

public class Move {

    public static final String ACTION_MOVE = "move";
    public static final String ACTION_SHOOT = "shoot";

    public static final String DIRECTION_NORTH = "north";
    public static final String DIRECTION_SOUTH = "south";
    public static final String DIRECTION_EAST = "east";
    public static final String DIRECTION_WEST = "west";

    @Expose
    private String playerId;
    @Expose
    private Move_ move;

    public Move() {
    }

    public Move(String playerId, Move_ move) {
        this.playerId = playerId;
        this.move = move;
    }

    /**
     * @return The playerId
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * @param playerId The playerId
     */
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * @return The action
     */
    public Move_ getMove() {
        return move;
    }

    /**
     * @param move The action
     */
    public void setMove(Move_ move) {
        this.move = move;
    }

    public class Move_ {

        Move_(String action, String direction) {
            this.action = action;
            this.direction = direction;
        }

        @Expose
        private String action;
        @Expose
        private String direction;

        /**
         * @return The action
         */
        public String getAction() {
            return action;
        }

        /**
         * @param action The action
         */
        public void setAction(String action) {
            this.action = action;
        }

        /**
         * @return The direction
         */
        public String getDirection() {
            return direction;
        }

        /**
         * @param direction The direction
         */
        public void setDirection(String direction) {
            this.direction = direction;
        }

    }

}