package nl.cinqict.handler;

import com.google.gson.JsonObject;
import nl.cinqict.Request;
import nl.cinqict.State;

public abstract class Handler {

    public void updateState(Request request) {
        // do nothing
    }

    ;

    public abstract String getReply();
}
