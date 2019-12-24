package nl.cinqict.voiceadventure.handler

import nl.cinqict.voiceadventure.world.Location

import static nl.cinqict.voiceadventure.world.Item.*

class KillHandlerTest extends HandlerTest {

    private KillHandler killHandler

    void setup() {
        state.getLocation() >> Location.CAVE
        parameters.getObject() >> TROLL.name()
        killHandler = new KillHandler()
    }

    void "the item can be killed (is on this location)"() {
        when:
        def reply = killHandler.updateState(request)
        then:
        KillHandler.HAPPY_REPLY == reply
    }

    void "the item cannot be killed (is not on this location)"() {
        when:
        def reply = killHandler.updateState(request)
        then:
        // the override the mock has to be here ...
        state.getLocation() >> Location.CASTLE
        reply == String.format(KillHandler.SPECIFIED_UNHAPPY_REPLY, TROLL.name)
    }

    void "the item does not exist"() {
        when:
        def reply = killHandler.updateState(request)
        then:
        // the override the mock has to be here ...
        parameters.getObject() >> ""
        reply == killHandler.UNSPECIFIED_UNHAPPY_REPLY
    }
}
