package nl.cinqict.handler;

import nl.cinqict.Request;
import nl.cinqict.State;
import nl.cinqict.WorldMap;

import java.awt.*;

public class MoveHandler extends Handler {

    private String reply;

    @Override
    public void updateState(Request request) {
        State state = request.getState();
        String direction = request.getParameters().get("direction").getAsString();
        Point delta = directionToPoint(direction);

        // check if the move is possible
        final int newX = state.getPosx() + delta.x;
        final int newY = state.getPosy() + delta.y;

        int absCoordinateSum = Math.abs(newX) + Math.abs(newY);
        // the sum of the absolute of x and y should never be higher than 1
        if (absCoordinateSum > 1) {
            // this move is not valid
            reply = "You cannot move there.";
            // do not change the location
        } else {
            reply = WorldMap.getDescription(newX, newY);
            // change the location
            state.setPosx(newX);
            state.setPosy(newY);
        }
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
        return reply;
    }
}
