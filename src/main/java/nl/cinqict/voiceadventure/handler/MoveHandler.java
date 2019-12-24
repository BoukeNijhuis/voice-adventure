package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Location;

import java.awt.*;

public class MoveHandler extends Handler {

    static final String CANNOT_MOVE_THERE = "You cannot move there.";

    @Override
    public String updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();
        Point delta = parameters.getDirection();

        Point newPosition = newPosition(state.getPosition(), delta);

        // check if the move is possible
        if (isValidPosition(newPosition)) {
            // change the location
            state.setPosition(newPosition);

            return Location.valueOf(newPosition).getDescription();
        } else {
            // this move is not valid
            return CANNOT_MOVE_THERE;
        }
    }

    private Point newPosition(Point position, Point delta) {
        final int newX = position.x + delta.x;
        final int newY = position.y + delta.y;
        return new Point(newX, newY);
    }


    private boolean isValidPosition(Point point) {
        int absCoordinateSum = Math.abs(point.x) + Math.abs(point.y);
        // the sum of the absolute of x and y should never be higher than 1
        return absCoordinateSum <= 1;
    }
}
