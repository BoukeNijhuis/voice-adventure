package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;

public class PickupHandler extends Handler {

    static final String HAPPY_REPLY = "Added the %s to the inventory";
    static final String UNHAPPY_REPLY = "Cannot pick this up.";

    @Override
    public void updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();
        Item item = Item.valueOf(parameters.getObject());


        if (canBePickedUp(item, state)) {
            state.addItem(item);
            reply = String.format(HAPPY_REPLY, item.getName());
        } else {
            reply = UNHAPPY_REPLY;
        }
    }

    /**
     * An item can only be picked up when:
     * - it is portable
     * - it is not picked up or used before
     * - you are standing in the right location
     */
    private boolean canBePickedUp(Item item, State state) {
        boolean portable = item.isPortable();
        boolean pristine = state.isPristine(item);
        boolean correctLocation = item.getLocation().equals(state.getLocation());

        return portable && pristine && correctLocation;
    }
}
