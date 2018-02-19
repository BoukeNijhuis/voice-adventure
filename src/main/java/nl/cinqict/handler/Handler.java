package nl.cinqict.handler;

import com.google.gson.JsonObject;
import nl.cinqict.Request;
import nl.cinqict.State;

public abstract class Handler {

    protected String reply;

    public void updateState(Request request) {
        // do nothing
    }

    public String getReply() {
        return reply;
    }
}
