package nl.cinqict.voiceadventure.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.cinqict.voiceadventure.DialogflowConstants;
import nl.cinqict.voiceadventure.JsonUtil;
import nl.cinqict.voiceadventure.world.Item;
import nl.cinqict.voiceadventure.world.Location;

import java.util.HashSet;
import java.util.Set;

public class State {

    private Location location = Location.CROSSING;
    private Set<Item> inventory = new HashSet<>();
    private Set<Item> removedItems = new HashSet<>();
    private Set<Location> visitedLocations = new HashSet<>();

    // for creating the default State object
    State() {
    }

    /**
     * Converts a JSON text to a State object
     *
     * @param stateParameters the json object that represents the state
     */
    State(JsonObject stateParameters) {
        JsonElement jsonElement = stateParameters.get(DialogflowConstants.LOCATION);
        if (jsonElement != null) {
            location = Location.valueOf(jsonElement.getAsString());
        }
        inventory = JsonUtil.getSet(Item.class, stateParameters, DialogflowConstants.INVENTORY);
        removedItems = JsonUtil.getSet(Item.class, stateParameters, DialogflowConstants.REMOVED_ITEMS);
        visitedLocations = JsonUtil.getSet(Location.class, stateParameters, DialogflowConstants.VISITED_LOCATIONS);
    }

    /**
     * Converts a State object to a JsonObject.
     *
     * @return a JSON representation of the state.
     */
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(DialogflowConstants.LOCATION, location.toString());
        addIfNotNull(jsonObject, DialogflowConstants.INVENTORY, inventory);
        addIfNotNull(jsonObject, DialogflowConstants.REMOVED_ITEMS, removedItems);
        addIfNotNull(jsonObject, DialogflowConstants.VISITED_LOCATIONS, visitedLocations);

        return jsonObject;
    }

    private <T extends Enum<T>> void addIfNotNull(JsonObject jsonObject, String key, Set<T> set) {
        if (set.size() > 0) {
            jsonObject.add(key, JsonUtil.getJsonArray(set));
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
        removedItems.add(item);
    }

    public boolean hasItem(Item item) {
        return inventory.contains(item);
    }

    /**
     * Checks if an item is untouched. Technically it should not be in the inventory or removed items.
     *
     * @param item to check
     * @return true when untouched, false when in inventory or used
     */
    public boolean isPristine(Item item) {
        return !inventory.contains(item) && !removedItems.contains(item);
    }

    public Set<Item> getInventory() {
        return inventory;
    }

    public Set<Location> getVisitedLocation() {
        return visitedLocations;
    }

    public void addVisitedLocation(Location newLocation) {
        visitedLocations.add(newLocation);
    }
}
