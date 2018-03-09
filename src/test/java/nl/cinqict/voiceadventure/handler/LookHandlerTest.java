package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.world.Item;
import nl.cinqict.voiceadventure.world.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        when(state.getLocation()).thenReturn(Location.WELL);
        lookHandler.updateState(request);
        assertEquals(Location.WELL.getDescription(), lookHandler.reply);
    }

    @Test
    void itemNotInThisLocation() {
        when(parameters.getObject()).thenReturn(Item.KEY.name());

        lookHandler.updateState(request);
        assertEquals(String.format(LookHandler.NOT_FOUND, "key"), lookHandler.reply);
    }

    @Test
    void itemInThisLocation() {
        when(parameters.getObject()).thenReturn(Item.KEY.name());
        when(state.getLocation()).thenReturn(Location.CAVE);

        lookHandler.updateState(request);
        assertEquals(Item.KEY.getDescription(), lookHandler.reply);
    }

    @Test
    void itemInInventory() {
        when(parameters.getObject()).thenReturn(Item.KEY.name());
        when(state.getLocation()).thenReturn(Location.WELL);
        when(state.hasItem(Item.KEY)).thenReturn(true);

        lookHandler.updateState(request);
        assertEquals(Item.KEY.getDescription(), lookHandler.reply);
    }
}
