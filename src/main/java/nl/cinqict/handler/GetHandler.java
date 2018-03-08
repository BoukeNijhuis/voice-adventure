package nl.cinqict.handler;

import nl.cinqict.message.Parameters;
import nl.cinqict.message.Request;
import nl.cinqict.message.State;
import nl.cinqict.world.Item;

public class GetHandler extends Handler {

    @Override
    public void updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();
        Item item = Item.valueOf(parameters.getObject());

        // TODO: add location check
        if (item != null && item.isPortable()) {
            state.addItem(item);
            reply = String.format("Added the %s to the inventory", item.name().toLowerCase());
        } else {
            reply = "Cannot pick this up.";
        }
    }
}
