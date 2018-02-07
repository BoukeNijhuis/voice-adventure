package nl.cinqict.handler;

import com.google.gson.JsonObject;

public abstract class Handler {

    public abstract void updateState(JsonObject stateParameters);

    public abstract String getReply();
}
