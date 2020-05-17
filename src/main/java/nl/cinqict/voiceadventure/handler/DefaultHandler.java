package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Request;

class DefaultHandler extends Handler {

    @Override
    public String updateState(Request request) {
        return "Default reply!";
    }
}
