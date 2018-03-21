package nl.cinqict.voiceadventure.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.cinqict.voiceadventure.DialogflowConstants;

import java.awt.*;

public class Parameters {

    private final JsonObject parameters;

    Parameters(JsonObject parameters) {
        this.parameters = parameters;
    }

    public Point getDirection() {
        String direction = getValue(DialogflowConstants.DIRECTION);
        return directionToPoint(direction);
    }

    private Point directionToPoint(String direction) {
        switch (direction) {
            case DialogflowConstants.NORTH:
                return new Point(0, 1);
            case DialogflowConstants.EAST:
                return new Point(1, 0);
            case DialogflowConstants.SOUTH:
                return new Point(0, -1);
            case DialogflowConstants.WEST:
                return new Point(-1, 0);
            default:
                return new Point(0, 0);
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
