package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;
import nl.cinqict.voiceadventure.world.Location;

public class KillHandler extends Handler {

    static final String HAPPY_REPLY = "What should I USE to kill it?";
    static final String SPECIFIED_UNHAPPY_REPLY = "There is no %s here.";
    static final String UNSPECIFIED_UNHAPPY_REPLY = "I cannot find and therefore not kill it.";

    @Override
    public void updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();
        Item item = Item.valueOfNullSafe(parameters.getObject());

        if (Item.UNKNOWN.equals(item)) {
            reply = UNSPECIFIED_UNHAPPY_REPLY;
            return;
        }

        Location location = state.getLocation();

        if (itemOnLocation(item, location)) {
            reply = HAPPY_REPLY;
        } else {
            reply = String.format(SPECIFIED_UNHAPPY_REPLY, item.getName());
        }
    }

    /**
     * An object can only be killed when :
     * - you are standing in the right location
     */
    private boolean itemOnLocation(Item item, Location location) {
        return item.getLocation().equals(location);
    }
}
