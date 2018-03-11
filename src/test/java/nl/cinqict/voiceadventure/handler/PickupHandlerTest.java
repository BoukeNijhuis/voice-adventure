package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.world.Item;
import nl.cinqict.voiceadventure.world.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PickupHandlerTest extends HandlerTest {

    private PickupHandler pickupHandler;

    @BeforeEach
    void setup() {
        super.setup();
        when(state.getLocation()).thenReturn(Location.CLEARING);
        pickupHandler = new PickupHandler();
    }

    @Test
    void theItemCanBePickedUp() {
        when(parameters.getObject()).thenReturn(Item.HANDLE.name());
        when(state.isPristine(Item.HANDLE)).thenReturn(true);

        pickupHandler.updateState(request);
        assertEquals(String.format(PickupHandler.HAPPY_REPLY, "handle"), pickupHandler.reply);
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

        pickupHandler.updateState(request);
        assertEquals(PickupHandler.UNHAPPY_REPLY, pickupHandler.reply);
    }

    @Test
    void theItemIsNotPristine() {
        when(parameters.getObject()).thenReturn(Item.HANDLE.name());
        when(state.isPristine(Item.HANDLE)).thenReturn(false);

        pickupHandler.updateState(request);
        assertEquals(PickupHandler.UNHAPPY_REPLY, pickupHandler.reply);
    }

    @Test
    void theItemIsNotInThisLocation() {
        when(parameters.getObject()).thenReturn(Item.KEY.name());

        pickupHandler.updateState(request);
        assertEquals(PickupHandler.UNHAPPY_REPLY, pickupHandler.reply);
    }
}
