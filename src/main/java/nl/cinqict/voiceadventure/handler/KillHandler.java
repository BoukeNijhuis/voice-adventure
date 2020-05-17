package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;
import nl.cinqict.voiceadventure.world.Location;

public class KillHandler extends Handler {

    private static final String HAPPY_REPLY = "What should I USE to kill it?";
    private static final String SPECIFIED_UNHAPPY_REPLY = "There is no %s here.";
    private static final String UNSPECIFIED_UNHAPPY_REPLY = "I cannot find and therefore not kill it.";

    @Override
    public String updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();

        Item item = Item.valueOfNullSafe(parameters.getObject());
        if (Item.UNKNOWN.equals(item)) {
            return UNSPECIFIED_UNHAPPY_REPLY;
        }

        Location location = state.getLocation();

        if (itemOnLocation(item, location)) {
            return HAPPY_REPLY;
        } else {
            return String.format(SPECIFIED_UNHAPPY_REPLY, item.getName());
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
