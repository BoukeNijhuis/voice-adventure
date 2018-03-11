package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;

public class UseHandler extends Handler {

    static final String CANNOT_BE_USED_ON_EACH_OTHER = "Cannot use %s on %s.";
    static final String CANNOT_USE_ONE_ITEM = "Where should I use the %s on?";
    static final String NOT_IN_INVENTORY = "There is no object called %s in your inventory.";
    static final String INCORRECT_LOCATION = "There is no object called %s in this location";

    @Override
    public void updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();
        Item itemA = Item.valueOf(parameters.getObject());
        Item itemB;
        if (parameters.getSecondObject() != null) {
            itemB = Item.valueOf(parameters.getSecondObject());
        } else {
            reply = String.format(CANNOT_USE_ONE_ITEM, itemA.getName());
            return;
        }

        final BooleanAndString bas = canBeUsed(itemA, itemB, state);
        if (bas.canBeUsed) {
            state.removeItem(itemA);
        }
        reply = bas.reply;
    }

    /**
     * An item A can only be used on item B when:
     * - item A is in the inventory
     * - item B is in the current location
     * - it makes sense to us item A on B
     */
    private BooleanAndString canBeUsed(Item itemA, Item itemB, State state) {
        boolean inInventory = state.hasItem(itemA);
        boolean correctLocation = itemB.getLocation().equals(state.getLocation());
        boolean makesSense = itemA.canBeUsedOn(itemB);

        BooleanAndString bas = new BooleanAndString();

        if (!inInventory) {
            bas.reply = String.format(NOT_IN_INVENTORY, itemA.getName());
        } else if (!correctLocation) {
            bas.reply = String.format(INCORRECT_LOCATION, itemB.getName());
        } else if (!makesSense) {
            bas.reply = String.format(CANNOT_BE_USED_ON_EACH_OTHER, itemA.getName(), itemB.getName());
        } else {
            bas.canBeUsed = true;
            bas.reply = itemA.getUseReply();
        }

        return bas;
    }

    private class BooleanAndString {
        boolean canBeUsed = false;
        String reply;
    }
}
