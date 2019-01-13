package main.model;

public enum Direction {
    NORTH("NORTH", "EAST"),
    SOUTH("SOUTH", "WEST"),
    EAST("EAST", "SOUTH"),
    WEST("WEST", "NORTH");

    String nextDirection;


    Direction(String direction, String nextDirection) {
        this.nextDirection = nextDirection;
    }

    public Direction getNextDirection() {
        return Direction.valueOf(nextDirection);
    }
}
