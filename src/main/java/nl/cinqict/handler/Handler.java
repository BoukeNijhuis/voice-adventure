package nl.cinqict.handler;

import nl.cinqict.message.Request;

public abstract class Handler {

    protected String reply;

    public void updateState(Request request) {
        // do nothing
    }

    public String getReply() {
        return reply;
    }
}
