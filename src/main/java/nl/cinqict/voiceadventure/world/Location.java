package nl.cinqict.voiceadventure.world;

import nl.cinqict.voiceadventure.message.Parameters;

public enum Location {

    WELL(-1, 0, "a roof of a well", "You are standing next to a dry well. The handle of the well is missing."),
    CASTLE(0, 1, "a big castle", "You are standing in front of a big castle. There is a door, but it is locked."),
    CROSSING(0, 0, "a crossing", "You are at a crossing. In the north you see a castle. In the east is a clearing in the woods. In the south is a cave. In the west is a well."),
    CAVE(0, -1, "a dark cave", "You entered the cave and there is a big troll coming your way."),
    CLEARING(1, 0, "a clearing", "You are standing in a clearing in the woods. There is a bush on your right-hand side.");

    private final int x, y;
    private final String shortDescription, longDescription;

    Location(int x, int y, String shortDescription, String longDescription) {
        this.x = x;
        this.y = y;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;

    }

    public static Location valueOf(int x, int y) {
        final Location[] locations = Location.values();

        // TODO: inefficient
        for (Location location : locations) {
            if (location.x == x && location.y == y) {
                return location;
            }
        }

        return null;
    }

    public Location move(Parameters.Direction direction) {
        if (isValidMove(direction)) {
            return newLocation(x, y, direction);
        } else return this;
    }

    private boolean isValidMove(Parameters.Direction direction) {
        int absCoordinateSum = Math.abs(this.x + direction.x) + Math.abs(this.y + direction.y);
        // the sum of the absolute of x and y should never be higher than 1
        return absCoordinateSum <= 1;
    }

    private Location newLocation(int x, int y, Parameters.Direction direction) {
        final int newX = x + direction.x;
        final int newY = y + direction.y;
        return valueOf(newX, newY);
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getShortDescription() {
        return "You see " + shortDescription + ".";
    }
}

