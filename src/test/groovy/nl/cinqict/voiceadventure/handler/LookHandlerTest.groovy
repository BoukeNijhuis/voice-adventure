package nl.cinqict.voiceadventure.handler

import nl.cinqict.voiceadventure.message.Parameters
import nl.cinqict.voiceadventure.world.Location

import static nl.cinqict.voiceadventure.world.Item.KEY
import static nl.cinqict.voiceadventure.world.Location.*

class LookHandlerTest extends HandlerTest {

    private LookHandler lookHandler = new LookHandler()

    protected void setup() {
        parameters.getDirection() >> Parameters.Direction.NONE;
    }

    void lookAtLocation() {
        state.getLocation() >> WELL
        when:
        def reply = lookHandler.updateState(request)
        then:
        WELL.getDescription() == reply
    }

    void itemNotInThisLocation() {
        parameters.getObject() >> KEY.name()
        when:
        def reply = lookHandler.updateState(request)
        then:
        String.format(LookHandler.ITEM_NOT_FOUND, "key") == reply
    }

    void itemInThisLocation() {
        parameters.getObject() >> (KEY.name())
        state.getLocation() >> Location.CAVE
        when:
        def reply = lookHandler.updateState(request)
        then:
        KEY.getDescription() == reply
    }

    void itemInInventory() {
        parameters.getObject() >> KEY.name()
        state.getLocation() >> WELL
        state.hasItem(KEY) >> true
        when:
        def reply = lookHandler.updateState(request)
        then:
        KEY.getDescription() == reply
    }

    void lookAtDirectionHappyFlow() {
        state.getLocation() >> CROSSING
        when:
        def reply = lookHandler.updateState(request)
        then:
        parameters.getDirection() >> Parameters.Direction.NORTH
        CASTLE.getDescription() == reply
    }

    void lookAtDirectionUnhappyFlow() {
        state.getLocation() >> CASTLE

        when:
        def reply = lookHandler.updateState(request)
        then:
        parameters.getDirection() >> Parameters.Direction.NORTH
        lookHandler.LOCATION_NOT_VALID == reply
    }
}
