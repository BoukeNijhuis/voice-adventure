package nl.cinqict.voiceadventure.message;

import com.google.gson.JsonObject;
import nl.cinqict.voiceadventure.JsonUtil;
import nl.cinqict.voiceadventure.DialogflowConstants;
import nl.cinqict.voiceadventure.world.Location;
import nl.cinqict.voiceadventure.world.Item;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class State {

    private int posx = 0;
    private int posy = 0;
    private Set<Item> inventory = new HashSet<>();

    State() {
    }

    /**
     * Converts a JSON text to a State object
     *
     * @param stateParameters the json object that represents the state
     */
    State(JsonObject stateParameters) {
        posx = stateParameters.get(DialogflowConstants.POSX).getAsInt();
        posy = stateParameters.get(DialogflowConstants.POSY).getAsInt();
        inventory = JsonUtil.getItemSet(stateParameters.get(DialogflowConstants.INVENTORY).getAsJsonArray());
    }

    /**
     * Converts a State object to a JsonObject.
     * @return a JSON representation of the state.
     */
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(DialogflowConstants.POSX, posx);
        jsonObject.addProperty(DialogflowConstants.POSY, posy);
        jsonObject.add(DialogflowConstants.INVENTORY, JsonUtil.getJsonArray(inventory));
        return jsonObject;
    }

    public Point getPosition() {
        return new Point(this.posx, this.posy);
    }

    public Location getLocation() {
        return Location.valueOf(getPosition());
    }

    public void setPosition(Point point) {
        this.posx = point.x;
        this.posy = point.y;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public boolean hasItem(Item item) {
        return inventory.contains(item);
    }


}
