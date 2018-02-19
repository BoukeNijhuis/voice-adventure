package nl.cinqict;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.*;

import static nl.cinqict.DialogflowConstants.POSX;
import static nl.cinqict.DialogflowConstants.POSY;

public class State {

    private int posx = 0;
    private int posy = 0;

    public State() {
    }

    public State(JsonObject stateParameters) {
        posx = stateParameters.get(POSX).getAsInt();
        posy = stateParameters.get(POSY).getAsInt();
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(POSX, posx);
        jsonObject.addProperty(POSY, posy);
        return jsonObject;
    }

    public Point getPosition() {
        return new Point(this.posx, this.posy);
    }

    public void setPosition(Point point) {
        this.posx = point.x;
        this.posy = point.y;
    }
}
