package nl.cinqict.message;

import com.google.gson.JsonObject;
import nl.cinqict.JsonUtil;
import nl.cinqict.world.Item;
import nl.cinqict.world.Location;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import static nl.cinqict.DialogflowConstants.INVENTORY;
import static nl.cinqict.DialogflowConstants.POSX;
import static nl.cinqict.DialogflowConstants.POSY;

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
        posx = stateParameters.get(POSX).getAsInt();
        posy = stateParameters.get(POSY).getAsInt();
        inventory = JsonUtil.getItemSet(stateParameters.get(INVENTORY).getAsJsonArray());
    }

    /**
     * Converts a State object to a JsonObject.
     * @return a JSON representation of the state.
     */
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(POSX, posx);
        jsonObject.addProperty(POSY, posy);
        jsonObject.add(INVENTORY, JsonUtil.getJsonArray(inventory));
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
