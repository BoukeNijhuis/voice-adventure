package nl.cinqict.message;

import com.google.gson.JsonObject;

import java.awt.*;

import static nl.cinqict.DialogflowConstants.*;

public class Parameters {

    private final JsonObject parameters;

    Parameters(JsonObject parameters) {
        this.parameters = parameters;
    }

    public Point getDirection() {
        String direction = getValue(DIRECTION);
        return directionToPoint(direction);
    }

    private Point directionToPoint(String direction) {
        switch (direction) {
            case NORTH:
                return new Point(0, 1);
            case EAST:
                return new Point(1, 0);
            case SOUTH:
                return new Point(0, -1);
            case WEST:
                return new Point(-1, 0);
            default:
                return new Point(0, 0);
        }
    }

    public String getObject() {
        return getValue(OBJECT);
    }

    private String getValue(String name) {
        if (parameters != null && parameters.get(name) != null) {
            return parameters.get(name).getAsString();
        } else {
            return null;
        }
    }
}
