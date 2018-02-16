package nl.cinqict.handler;

import nl.cinqict.Request;
import nl.cinqict.State;
import nl.cinqict.WorldMap;

public class LookHandler extends Handler {

    private String reply;

    @Override
    public void updateState(Request request) {
        State state = request.getState();
        reply = WorldMap.getDescription(state.getPosx(), state.getPosy());
    }

    @Override
    public String getReply() {
        return reply;
    }
}
