package nl.cinqict.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.cinqict.Request;
import nl.cinqict.State;

import java.awt.*;

public class MoveHandler extends Handler {

    @Override
    public void updateState(Request request) {

        State state = request.getState();
        String direction = request.getParameters().get("direction").getAsString();
        Point delta = directionToPoint(direction);

        state.setPosx(state.getPosx() + delta.x);
        state.setPosy(state.getPosy() + delta.y);
    }

    private Point directionToPoint(String direction) {
        switch (direction) {
            case "N":
                return new Point(0, 1);
            case "E":
                return new Point(1, 0);
            case "S":
                return new Point(0, -1);
            case "W":
                return new Point(-1, 0);
            default:
                return new Point(0, 0);
        }
    }

    @Override
    public String getReply() {
        return "You moved.";
    }
}
