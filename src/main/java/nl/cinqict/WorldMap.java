package nl.cinqict;

import java.awt.*;
import java.util.HashMap;

public class WorldMap {

    private static HashMap<Point, String> map = new HashMap<>();

    static {
        addLocation(-1, 0, "You are standing next to a dry well. The handle of the well is missing. ");
        addLocation(0, 1, "You are standing in front of a big castle. There is a door, but it is locked.");
        addLocation(0, 0, "You are at a crossing. In the north you see a castle. In the east is a clearing in the woods. In the south is a cave. In the west is a well.");
        addLocation(0, -1, "You entered the cave and there is a big troll coming your way.");
        addLocation(1, 0, "You are standing in a clearing in the woods. There is a bush on your right-hand side.");
    }

    private static void addLocation(int x, int y, String description) {
        map.put(new Point(x, y), description);
    }

    public static String getDescription(Point point) {
        return map.get(point);
    }
}
