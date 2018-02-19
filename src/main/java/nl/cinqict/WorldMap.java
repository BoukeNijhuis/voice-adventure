package nl.cinqict;

import java.awt.*;
import java.util.HashMap;

public class WorldMap {

    private static HashMap<Point, String> map = new HashMap<>();

    static {
        addLocation(-1, 0, "You are West.");
        addLocation(0, 1, "You are North.");
        addLocation(0, 0, "You are Center.");
        addLocation(0, -1, "You are South.");
        addLocation(1, 0, "You are East.");
    }

    private static void addLocation(int x, int y, String description) {
        map.put(new Point(x, y), description);
    }

    public static String getDescription(Point point) {
        return map.get(point);
    }
}
