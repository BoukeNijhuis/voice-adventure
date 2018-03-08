package nl.cinqict.handler;

import nl.cinqict.message.Parameters;
import nl.cinqict.message.Request;
import nl.cinqict.message.State;
import nl.cinqict.world.Item;
import nl.cinqict.world.Location;

public class LookHandler extends Handler {

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
                reply = String.format("I do not see a %s", item.name().toLowerCase());
            }
        }
        // looking at an location
        else {
            reply = state.getLocation().getDescription();
        }
    }
}
