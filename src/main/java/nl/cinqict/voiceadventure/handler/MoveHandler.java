package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Location;

public class MoveHandler extends Handler {

    private static final String CANNOT_MOVE_THERE = "You cannot move there.";

    @Override
    public String updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();
        Parameters.Direction direction = parameters.getDirection();
        Location currentLocation = state.getLocation();
        String reply = CANNOT_MOVE_THERE;

        // what will be the new location
        Location newLocation = currentLocation.move(direction);

        // check if the move is executed
        if (newLocation != currentLocation) {

            // change the location
            state.setLocation(newLocation);

            if (state.getVisitedLocation().contains(newLocation)) {
                reply = newLocation.getShortDescription();
            } else {
                reply = newLocation.getLongDescription();
            }

            // update the visited locations
            state.addVisitedLocation(newLocation);
        }

        return reply;
    }
}
