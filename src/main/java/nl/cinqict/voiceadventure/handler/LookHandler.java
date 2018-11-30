package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;

public class LookHandler extends Handler {

    static final String NOT_FOUND = "I do not see a %s";

    @Override
    public void updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();

        // looking at an object
        if (parameters.getObject() != null) {
            Item item = Item.valueOf(parameters.getObject());
            // the object should be in the inventory or on this location
            if (state.hasItem(item) || item.getLocation().equals(state.getLocation())) {
                reply = item.getDescription();
            } else {
                // TODO: differentiate between a and an?
                reply = String.format(NOT_FOUND, item.getName());
            }
        }
        // looking at an location
        // TODO: items can already be picked up! (so need different descriptions)
        else {
            reply = state.getLocation().getDescription();
        }
    }
}
