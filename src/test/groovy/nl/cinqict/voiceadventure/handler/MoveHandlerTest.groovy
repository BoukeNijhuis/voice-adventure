package nl.cinqict.voiceadventure.handler

import nl.cinqict.voiceadventure.message.Parameters

import static nl.cinqict.voiceadventure.world.Location.CASTLE
import static nl.cinqict.voiceadventure.world.Location.CLEARING
import static nl.cinqict.voiceadventure.world.Location.CROSSING

class MoveHandlerTest extends HandlerTest {

    private MoveHandler moveHandler

    protected void setup() {
        moveHandler = new MoveHandler()
    }

    void moveSomewhereThatIsPossible() {
        state.getLocation() >> CROSSING
        parameters.getDirection() >> Parameters.Direction.NORTH
        when:
        def reply = moveHandler.updateState(request)
        then:
        CASTLE.getLongDescription() == reply
    }

    void moveSomewhereThatIsImpossible() {
        state.getLocation() >> CLEARING
        parameters.getDirection() >> Parameters.Direction.EAST
        when:
        def reply = moveHandler.updateState(request)
        then:
        MoveHandler.CANNOT_MOVE_THERE == reply
    }
}
