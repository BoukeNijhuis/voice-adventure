package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;

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
