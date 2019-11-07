package nl.cinqict.voiceadventure.handler

import nl.cinqict.voiceadventure.world.Location

import static nl.cinqict.voiceadventure.world.Item.HANDLE
import static nl.cinqict.voiceadventure.world.Item.SWORD
import static nl.cinqict.voiceadventure.world.Item.WELL

class UseHandlerSpec extends HandlerSpec {

    private UseHandler useHandler

    void setup() {
        state.getLocation() >> Location.WELL
        parameters.getObject() >> HANDLE.name()
        parameters.getSecondObject() >> WELL.name()
        state.hasItem(HANDLE) >> true
        useHandler = new UseHandler()
    }

    void theItemsCanBeUsedOnEachOther() {
        when:
        useHandler.updateState(request)
        then:
        HANDLE.getUseReply() == useHandler.reply
    }

    /**
     * An item A can only be used on item B when:
     * - item A is in the inventory
     * - item B is in the current location
     * - it makes sense to us item A on B
     */
    void theFirstItemIsNotInTheInventory() {
        when:
        useHandler.updateState(request)
        then:
        state.hasItem(HANDLE) >> false
        String.format(UseHandler.NOT_IN_INVENTORY, HANDLE.getName()) == useHandler.reply
    }

    void unknownItem() {
        when:
        useHandler.updateState(request)
        then:
        parameters.getObject() >> null
        UseHandler.UNKNOWN_ITEM == useHandler.reply
    }

    void thereIsNoSecondItem() {
        when:
        useHandler.updateState(request)
        then:
        parameters.getSecondObject() >> null
        String.format(UseHandler.CANNOT_USE_ONE_ITEM, HANDLE.getName()) == useHandler.reply
    }

    void theSecondItemIsNotInThisLocation() {
        when:
        useHandler.updateState(request)
        then:
        state.getLocation() >> Location.CASTLE
        String.format(UseHandler.INCORRECT_LOCATION, WELL.getName()) == useHandler.reply
    }

    void theObjectsCannotBeUsedOnEachOther() {
        when:
        useHandler.updateState(request)
        then:
        parameters.getSecondObject() >> SWORD.name()
        String.format(UseHandler.CANNOT_BE_USED_ON_EACH_OTHER, HANDLE.getName(), SWORD.getName()) == useHandler.reply
    }
}
