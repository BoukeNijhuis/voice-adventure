package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.world.Item;
import nl.cinqict.voiceadventure.world.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.cinqict.voiceadventure.world.Item.KEY;
import static nl.cinqict.voiceadventure.world.Location.WELL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LookHandlerTest extends HandlerTest {

    private LookHandler lookHandler;

    @BeforeEach
    void setup() {
        super.setup();
        lookHandler = new LookHandler();
    }

    @Test
    void lookAtLocation() {
        when(state.getLocation()).thenReturn(WELL);
        lookHandler.updateState(request);
        assertEquals(WELL.getDescription(), lookHandler.reply);
    }

    @Test
    void itemNotInThisLocation() {
        when(parameters.getObject()).thenReturn(KEY.name());

        lookHandler.updateState(request);
        assertEquals(String.format(LookHandler.NOT_FOUND, "key"), lookHandler.reply);
    }

    @Test
    void itemInThisLocation() {
        when(parameters.getObject()).thenReturn(KEY.name());
        when(state.getLocation()).thenReturn(Location.CAVE);

        lookHandler.updateState(request);
        assertEquals(KEY.getDescription(), lookHandler.reply);
    }

    @Test
    void itemInInventory() {
        when(parameters.getObject()).thenReturn(KEY.name());
        when(state.getLocation()).thenReturn(WELL);
        when(state.hasItem(KEY)).thenReturn(true);

        lookHandler.updateState(request);
        assertEquals(KEY.getDescription(), lookHandler.reply);
    }
}
