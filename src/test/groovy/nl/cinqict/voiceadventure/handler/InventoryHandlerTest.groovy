package nl.cinqict.voiceadventure.handler

import nl.cinqict.voiceadventure.world.Item

import static nl.cinqict.voiceadventure.world.Item.HANDLE
import static nl.cinqict.voiceadventure.world.Item.KEY
import static nl.cinqict.voiceadventure.world.Item.SWORD

class InventoryHandlerTest extends HandlerTest {

    private InventoryHandler inventoryHandler

    void setup() {
        inventoryHandler = new InventoryHandler()
    }

    void emptyInventory() {
        state.getInventory() >> new HashSet<>()
        when:
        inventoryHandler.updateState(request)
        then:
        InventoryHandler.EMPTY_INVENTORY == inventoryHandler.reply
    }

    void inventoryWithOneItem() {
        final HashSet<Item> inventory = new HashSet<>()
        inventory.add(KEY)
        state.getInventory() >> inventory
        when:
        inventoryHandler.updateState(request)
        then:
        String.format(InventoryHandler.ONE_ITEM, KEY.getName()) == inventoryHandler.reply
    }

    void inventoryWithThreeItems() {
        final HashSet<Item> inventory = new HashSet<>()
        inventory.add(KEY)
        inventory.add(SWORD)
        inventory.add(HANDLE)
        state.getInventory() >> inventory
        when:
        inventoryHandler.updateState(request)
        then:
        inventoryHandler.getReply().contains(KEY.getName())
        inventoryHandler.getReply().contains(SWORD.getName())
        inventoryHandler.getReply().contains(HANDLE.getName())
    }
}
