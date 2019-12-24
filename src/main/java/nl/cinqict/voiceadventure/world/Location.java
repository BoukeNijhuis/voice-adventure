package nl.cinqict.voiceadventure.world;

import java.awt.Point;

public enum Location {

    WELL(-1, 0, "You are standing next to a dry well. The handle of the well is missing."),
    CASTLE(0, 1, "You are standing in front of a big castle. There is a door, but it is locked."),
    CROSSING(0, 0, "You are at a crossing. In the north you see a castle. In the east is a clearing in the woods. In the south is a cave. In the west is a well."),
    CAVE(0, -1, "You entered the cave and there is a big troll coming your way."),
    CLEARING(1, 0, "You are standing in a clearing in the woods. There is a bush on your right-hand side.");

    private final int x, y;
    private final String description;

    Location(int x, int y, String description) {
        this.x = x;
        this.y = y;
        this.description = description;

    }

    public static Location valueOf(Point point) {
        final Location[] locations = Location.values();

        // TODO: inefficient
        for (Location location : locations) {
            if (location.x == point.x && location.y == point.y) {
                return location;
            }
        }

        return null;
    }

    public String getDescription() {
        return description;
    }
}

