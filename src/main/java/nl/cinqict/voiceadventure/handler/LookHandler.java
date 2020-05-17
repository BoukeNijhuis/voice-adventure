package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;
import nl.cinqict.voiceadventure.world.Location;

public class LookHandler extends Handler {

    private static final String ITEM_NOT_FOUND = "I do not see a %s.";
    private static final String LOCATION_NOT_VALID = "You see impenetrable mountains.";

    @Override
    public String updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();

        Item item = Item.valueOfNullSafe(parameters.getObject());

        // looking at an object?
        if (Item.UNKNOWN != item) {
            return lookAtObject(state, item);
        }
        // looking at a location?
        else {
            return lookAtLocation(state, parameters);
        }
    }

    private String lookAtLocation(State state, Parameters parameters) {
        Parameters.Direction direction = parameters.getDirection();
        if (direction != Parameters.Direction.NONE) {
            Location location = state.getLocation();
            Location newLocation = location.move(direction);
            if (newLocation != location) {
                return newLocation.getShortDescription();
            } else {
                return LOCATION_NOT_VALID;
            }
        } else {
            return state.getLocation().getLongDescription();
        }
    }

    private String lookAtObject(State state, Item item) {
        // the object should be in the inventory or on this location
        if (state.hasItem(item) || item.getLocation().equals(state.getLocation())) {
            return item.getDescription();
        } else {
            // TODO: differentiate between a and an?
            return String.format(ITEM_NOT_FOUND, item.getName());
        }
    }
}
