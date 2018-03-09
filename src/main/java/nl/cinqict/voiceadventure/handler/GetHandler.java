package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;

public class GetHandler extends Handler {

    static final String HAPPY_REPLY = "Added the %s to the inventory";
    static final String UNHAPPY_REPLY = "Cannot pick this up.";

    @Override
    public void updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();
        Item item = Item.valueOf(parameters.getObject());

        /*
        Items can only be picked up when:
        - they are portable
        - they are not picked up before or used before
        - you are standing in the right location
         */
        if (item.isPortable() && state.isPristine(item) && item.getLocation().equals(state.getLocation())) {
            state.addItem(item);
            reply = String.format(HAPPY_REPLY, item.name().toLowerCase());
        } else {
            reply = UNHAPPY_REPLY;
        }
    }
}
