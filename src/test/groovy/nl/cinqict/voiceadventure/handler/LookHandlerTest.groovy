package nl.cinqict.voiceadventure.handler


import nl.cinqict.voiceadventure.world.Location

import static nl.cinqict.voiceadventure.world.Item.KEY
import static nl.cinqict.voiceadventure.world.Location.WELL

class LookHandlerTest extends HandlerTest {

    private LookHandler lookHandler = new LookHandler()

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
        String.format(LookHandler.NOT_FOUND, "key") == reply
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
}
