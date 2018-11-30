package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.world.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static nl.cinqict.voiceadventure.world.Item.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class InventoryHandlerTest extends HandlerTest {

    private InventoryHandler inventoryHandler;

    @BeforeEach
    void setup() {
        super.setup();
        inventoryHandler = new InventoryHandler();
    }

    @Test
    void emptyInventory() {
        when(state.getInventory()).thenReturn(new HashSet<>());
        inventoryHandler.updateState(request);
        assertEquals(InventoryHandler.EMPTY_INVENTORY, inventoryHandler.reply);
    }

    @Test
    void inventoryWithOneItem() {

        final HashSet<Item> inventory = new HashSet<>();
        inventory.add(KEY);
        when(state.getInventory()).thenReturn(inventory);
        inventoryHandler.updateState(request);
        assertEquals(String.format(InventoryHandler.ONE_ITEM, KEY.getName()), inventoryHandler.reply);
    }

    @Test
    void inventoryWithThreeItems() {

        final HashSet<Item> inventory = new HashSet<>();
        inventory.add(KEY);
        inventory.add(SWORD);
        inventory.add(HANDLE);
        when(state.getInventory()).thenReturn(inventory);
        inventoryHandler.updateState(request);
        assertTrue(inventoryHandler.getReply().contains(KEY.getName()));
        assertTrue(inventoryHandler.getReply().contains(SWORD.getName()));
        assertTrue(inventoryHandler.getReply().contains(HANDLE.getName()));
    }
}
