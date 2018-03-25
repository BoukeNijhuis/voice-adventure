package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.world.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static nl.cinqict.voiceadventure.world.Location.CASTLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MoveHandlerTest extends HandlerTest {

    private MoveHandler moveHandler;

    @BeforeEach
    void setup() {
        super.setup();
        moveHandler = new MoveHandler();
    }

    @Test
    void moveSomewhereThatIsPossible() {
        when(state.getPosition()).thenReturn(new Point(0, 0));
        when(parameters.getDirection()).thenReturn(new Point(0, 1));

        moveHandler.updateState(request);
        assertEquals(CASTLE.getDescription(), moveHandler.reply);
    }

    @Test
    void moveSomewhereThatIsImpossible() {
        when(state.getPosition()).thenReturn(new Point(0, 1));
        when(parameters.getDirection()).thenReturn(new Point(0, 1));

        moveHandler.updateState(request);
        assertEquals(MoveHandler.CANNOT_MOVE_THERE, moveHandler.reply);
    }
}
