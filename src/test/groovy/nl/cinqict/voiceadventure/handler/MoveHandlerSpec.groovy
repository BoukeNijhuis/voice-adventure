package nl.cinqict.voiceadventure.handler

import java.awt.*

import static nl.cinqict.voiceadventure.world.Location.CASTLE

class MoveHandlerSpec extends HandlerSpec {

    private MoveHandler moveHandler

    protected void setup() {
        moveHandler = new MoveHandler()
    }

    void moveSomewhereThatIsPossible() {
        state.getPosition() >> new Point(0, 0)
        parameters.getDirection() >> new Point(0, 1)
        when:
        moveHandler.updateState(request)
        then:
        CASTLE.getDescription() == moveHandler.reply
    }

    void moveSomewhereThatIsImpossible() {
        state.getPosition() >> new Point(0, 1)
        parameters.getDirection() >> new Point(0, 1)
        when:
        moveHandler.updateState(request)
        then:
        MoveHandler.CANNOT_MOVE_THERE == moveHandler.reply
    }
}
