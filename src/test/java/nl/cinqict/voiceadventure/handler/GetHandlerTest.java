package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.world.Item;
import nl.cinqict.voiceadventure.world.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetHandlerTest extends HandlerTest {

    private GetHandler getHandler;

    @BeforeEach
    void setup() {
        super.setup();
        when(state.getLocation()).thenReturn(Location.CLEARING);
        getHandler = new GetHandler();
    }

    @Test
    void theItemCanBePickedUp() {
        when(parameters.getObject()).thenReturn(Item.HANDLE.name());
        when(state.isPristine(Item.HANDLE)).thenReturn(true);

        getHandler.updateState(request);
        assertEquals(String.format(GetHandler.HAPPY_REPLY, "handle"), getHandler.reply);
    }

    /*
    Items can only be picked up when:
    - they are portable
    - they are not picked up before or used before
    - you are standing in the right location
     */

    @Test
    void theItemIsNotPortable() {
        when(parameters.getObject()).thenReturn(Item.CASTLE.name());

        getHandler.updateState(request);
        assertEquals(GetHandler.UNHAPPY_REPLY, getHandler.reply);
    }

    @Test
    void theItemIsNotPristine() {
        when(parameters.getObject()).thenReturn(Item.HANDLE.name());
        when(state.isPristine(Item.HANDLE)).thenReturn(false);

        getHandler.updateState(request);
        assertEquals(GetHandler.UNHAPPY_REPLY, getHandler.reply);
    }

    @Test
    void theItemIsNotInThisLocation() {
        when(parameters.getObject()).thenReturn(Item.KEY.name());

        getHandler.updateState(request);
        assertEquals(GetHandler.UNHAPPY_REPLY, getHandler.reply);
    }
}
