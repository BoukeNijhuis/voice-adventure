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
        Parameters.Direction direction = parameters.getDirection();
        Location location = state.getLocation();

        // what will be the new location
        Location newLocation = location.move(direction);

        // check if the move is executed
        if (newLocation != location) {
            // change the location
            state.setPosition(newLocation);
            return newLocation.getDescription();
        } else {
            // this move is not valid
            return CANNOT_MOVE_THERE;
        }
    }
}
