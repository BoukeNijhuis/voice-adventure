package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.world.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.cinqict.voiceadventure.world.Item.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UseHandlerTest extends HandlerTest {

    private UseHandler useHandler;

    @BeforeEach
    void setup() {
        super.setup();
        when(state.getLocation()).thenReturn(Location.WELL);
        when(parameters.getObject()).thenReturn(HANDLE.name());
        when(parameters.getSecondObject()).thenReturn(WELL.name());
        when(state.hasItem(HANDLE)).thenReturn(true);
        useHandler = new UseHandler();
    }

    @Test
    void theItemsCanBeUsedOnEachOther() {
        useHandler.updateState(request);
        assertEquals(HANDLE.getUseReply(), useHandler.reply);
    }

    /**
     * An item A can only be used on item B when:
     * - item A is in the inventory
     * - item B is in the current location
     * - it makes sense to us item A on B
     */

    @Test
    void theFirstItemIsNotInTheInventory() {
        when(state.hasItem(HANDLE)).thenReturn(false);

        useHandler.updateState(request);
        assertEquals(String.format(UseHandler.NOT_IN_INVENTORY, HANDLE.getName()), useHandler.reply);
    }

    @Test
    void unknownItem() {
        when(parameters.getObject()).thenReturn(null);

        useHandler.updateState(request);
        assertEquals(UseHandler.UNKNOWN_ITEM, useHandler.reply);
    }

    @Test
    void thereIsNoSecondItem() {
        when(parameters.getSecondObject()).thenReturn(null);

        useHandler.updateState(request);
        assertEquals(String.format(UseHandler.CANNOT_USE_ONE_ITEM, HANDLE.getName()), useHandler.reply);
    }

    @Test
    void theSecondItemIsNotInThisLocation() {
        when(state.getLocation()).thenReturn(Location.CASTLE);

        useHandler.updateState(request);
        assertEquals(String.format(UseHandler.INCORRECT_LOCATION, WELL.getName()), useHandler.reply);
    }

    @Test
    void theObjectsCannotBeUsedOnEachOther() {
        when(parameters.getSecondObject()).thenReturn(SWORD.name());

        useHandler.updateState(request);
        assertEquals(String.format(UseHandler.CANNOT_BE_USED_ON_EACH_OTHER, HANDLE.getName(), SWORD.getName()), useHandler.reply);
    }
}
