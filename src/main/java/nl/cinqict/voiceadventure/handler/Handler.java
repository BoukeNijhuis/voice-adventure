package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Request;

public abstract class Handler {

    protected String reply;

    public void updateState(Request request) {
        // do nothing
    }

    public String getReply() {
        return reply;
    }
}
