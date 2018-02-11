package nl.cinqict;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class State {

    private int posx = 0;
    private int posy = 0;

    public State() {
    }

    public State(JsonObject stateParameters) {
        posx = stateParameters.get("posx").getAsInt();
        posy = stateParameters.get("posy").getAsInt();
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("posx", posx);
        jsonObject.addProperty("posy", posy);
        return jsonObject;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }
}
