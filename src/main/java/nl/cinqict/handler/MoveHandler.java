package nl.cinqict.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.cinqict.State;

public class MoveHandler extends Handler {

    @Override
    public void updateState(State state) {

        state.setPosx(state.getPosx() + 1);
        state.setPosy(state.getPosy() + 1);
    }

    @Override
    public String getReply() {
        return "You moved.";
    }
}
