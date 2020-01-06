package nl.cinqict.voiceadventure.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.cinqict.voiceadventure.DialogflowConstants;

import java.awt.*;

public class Parameters {

    private final JsonObject parameters;

    public static class Direction extends Point {

        public static final Direction NORTH = new Direction(0, 1);
        public static final Direction SOUTH = new Direction(0, -1);
        public static final Direction EAST = new Direction(1, 0);
        public static final Direction WEST = new Direction(-1, 0);
        public static final Direction NONE = new Direction(0, 0);

        Direction(int x, int y) {
            super(x,y);
        }
    }

    Parameters(JsonObject parameters) {
        this.parameters = parameters;
    }

    public Direction getDirection() {
        String direction = getValue(DialogflowConstants.DIRECTION);
        if (direction != null) {
            return directionToPoint(direction);
        } else {
            return Direction.NONE;
        }
    }

    private Direction directionToPoint(String direction) {
        switch (direction) {
            case DialogflowConstants.NORTH:
                return Direction.NORTH;
            case DialogflowConstants.EAST:
                return Direction.EAST;
            case DialogflowConstants.SOUTH:
                return Direction.SOUTH;
            case DialogflowConstants.WEST:
                return Direction.WEST;
            default:
                return Direction.NONE;
        }
    }

    public String getObject() {
        return getValue(DialogflowConstants.OBJECT);
    }

    public String getSecondObject() {
        return getValue(DialogflowConstants.OBJECT1);
    }

    private String getValue(String name) {

        if (parameters != null) {
            final JsonElement jsonElement = parameters.get(name);
            if (jsonElement != null) {
                // happens when an unknown object is used
                if (jsonElement.isJsonArray()) {
                    return null;
                }

                final String value = jsonElement.getAsString();
                if (!"".equals(value)) {
                    return value.toUpperCase();
                }
            }


        }

        return null;
    }
}
