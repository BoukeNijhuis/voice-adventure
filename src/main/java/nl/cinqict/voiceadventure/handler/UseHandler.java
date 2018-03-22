package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import nl.cinqict.voiceadventure.world.Item;

public class UseHandler extends Handler {

    static final String UNKNOWN_ITEM = "I do not know how to do this.";
    static final String CANNOT_BE_USED_ON_EACH_OTHER = "Cannot use %s on %s.";
    static final String CANNOT_USE_ONE_ITEM = "Where should I use the %s on?";
    static final String NOT_IN_INVENTORY = "There is no object called %s in your inventory.";
    static final String INCORRECT_LOCATION = "There is no object called %s in this location";

    @Override
    public void updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();

        Item itemA;
        final String object = parameters.getObject();
        if (object != null) {
            itemA = Item.valueOf(object);
        } else {
            reply = String.format(UNKNOWN_ITEM);
            return;
        }

        Item itemB;
        final String secondObject = parameters.getSecondObject();
        if (secondObject != null) {
            itemB = Item.valueOf(secondObject);
        } else {
            reply = String.format(CANNOT_USE_ONE_ITEM, itemA.getName());
            return;
        }

        final UseResult useResult = canBeUsed(itemA, itemB, state);
        if (useResult.canBeUsed) {
            state.removeItem(itemA);
            state.addItem(useResult.reward);
        }
        reply = useResult.reply;
    }

    /**
     * An item A can only be used on item B when:
     * - item A is in the inventory
     * - item B is in the current location
     * - it makes sense to us item A on B
     */
    private UseResult canBeUsed(Item itemA, Item itemB, State state) {
        boolean inInventory = state.hasItem(itemA);
        boolean correctLocation = itemB.getLocation().equals(state.getLocation());
        boolean makesSense = itemA.canBeUsedOn(itemB);

        UseResult useResult = new UseResult();

        if (!inInventory) {
            useResult.reply = String.format(NOT_IN_INVENTORY, itemA.getName());
        } else if (!correctLocation) {
            useResult.reply = String.format(INCORRECT_LOCATION, itemB.getName());
        } else if (!makesSense) {
            useResult.reply = String.format(CANNOT_BE_USED_ON_EACH_OTHER, itemA.getName(), itemB.getName());
        } else {
            useResult.canBeUsed = true;
            useResult.reply = itemA.getUseReply();
            useResult.reward = itemA.getUseReward();
        }

        return useResult;
    }

    private class UseResult {
        boolean canBeUsed = false;
        String reply;
        Item reward;
    }
}
