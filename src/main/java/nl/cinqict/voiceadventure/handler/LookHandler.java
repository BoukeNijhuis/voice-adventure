package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;
import nl.cinqict.voiceadventure.world.Location;

public class LookHandler extends Handler {

    static final String ITEM_NOT_FOUND = "I do not see a %s.";
    static final String UNSPECIFIED_NOT_FOUND = "I do not see that.";
    static final String LOCATION_NOT_VALID = "There is nothing to see.";

    @Override
    public String updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();

        Item item = Item.valueOfNullSafe(parameters.getObject());

        // looking at an object?
        if (Item.UNKNOWN != item) {
            // the object should be in the inventory or on this location
            if (state.hasItem(item) || item.getLocation().equals(state.getLocation())) {
                return item.getDescription();
            } else {
                // TODO: differentiate between a and an?
                return String.format(ITEM_NOT_FOUND, item.getName());
            }
        }
        // looking at a location?
        else {
            Parameters.Direction direction = parameters.getDirection();
            if (direction != Parameters.Direction.NONE) {
                Location location = state.getLocation();
                Location newLocation = location.move(direction);
                if (newLocation != location) {
                    return newLocation.getDescription();
                } else {
                    return LOCATION_NOT_VALID;
                }
            }

            // TODO: items can already be picked up! (so need different descriptions)
            else {
                return state.getLocation().getDescription();
            }
        }
    }
}
