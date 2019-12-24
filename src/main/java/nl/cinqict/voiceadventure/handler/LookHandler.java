package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;

public class LookHandler extends Handler {

    static final String NOT_FOUND = "I do not see a %s.";
    static final String UNSPECIFIED_NOT_FOUND = "I do not see that.";

    @Override
    public String updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();

        Item item = Item.valueOfNullSafe(parameters.getObject());

        // looking at an object
        if (Item.UNKNOWN != item) {
            // the object should be in the inventory or on this location
            if (state.hasItem(item) || item.getLocation().equals(state.getLocation())) {
                return item.getDescription();
            } else {
                // TODO: differentiate between a and an?
                return String.format(NOT_FOUND, item.getName());
            }
        }
        // looking at an location
        // TODO: items can already be picked up! (so need different descriptions)
        else {
            return state.getLocation().getDescription();
        }
    }
}
