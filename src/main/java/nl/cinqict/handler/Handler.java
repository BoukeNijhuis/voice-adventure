package nl.cinqict.handler;

import com.google.gson.JsonObject;
import nl.cinqict.State;

public abstract class Handler {

    public void updateState(State state) {
        // do nothing
    }

    ;

    public abstract String getReply();
}
