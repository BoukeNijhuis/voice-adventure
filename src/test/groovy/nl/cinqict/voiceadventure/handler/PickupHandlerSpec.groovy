package nl.cinqict.voiceadventure.handler

import nl.cinqict.voiceadventure.world.Location

import static nl.cinqict.voiceadventure.world.Item.CASTLE
import static nl.cinqict.voiceadventure.world.Item.HANDLE
import static nl.cinqict.voiceadventure.world.Item.KEY

class PickupHandlerSpec extends HandlerSpec {

    private PickupHandler pickupHandler

    void setup() {
        state.getLocation() >> Location.CLEARING
        pickupHandler = new PickupHandler()
    }

    void theItemCanBePickedUp() {
        parameters.getObject() >> HANDLE.name()
        state.isPristine(HANDLE) >> true
        when:
        pickupHandler.updateState(request)
        then:
        String.format(PickupHandler.HAPPY_REPLY, "handle") == pickupHandler.reply
    }

    /*
    Items can only be picked up when:
    - they are portable
    - they are not picked up before or used before
    - you are standing in the right location
     */

    void theItemIsNotPortable() {
        parameters.getObject() >> CASTLE.name()
        when:
        pickupHandler.updateState(request)
        then:
        PickupHandler.UNHAPPY_REPLY == pickupHandler.reply
    }

    void theItemIsNotPristine() {
        parameters.getObject() >> HANDLE.name()
        state.isPristine(HANDLE) >> false
        when:
        pickupHandler.updateState(request)
        then:
        PickupHandler.UNHAPPY_REPLY == pickupHandler.reply
    }

    void theItemIsNotInThisLocation() {
        parameters.getObject() >> KEY.name()
        when:
        pickupHandler.updateState(request)
        then:
        PickupHandler.UNHAPPY_REPLY == pickupHandler.reply
    }
}
