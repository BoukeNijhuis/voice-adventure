package nl.cinqict.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LookHandler extends Handler {

    @Override
    public void updateState(JsonObject stateParameters) {
        final JsonElement x = stateParameters.get("posx");
        String posx = x != null ? x.getAsString() : "";
        stateParameters.addProperty("posx", posx + "x");


        final JsonElement y = stateParameters.get("posy");
        String posy = y != null ? y.getAsString() : "";
        stateParameters.addProperty("posy", posy + "y");
    }

    @Override
    public String getReply() {
        return "You are in a forest";
    }
}
