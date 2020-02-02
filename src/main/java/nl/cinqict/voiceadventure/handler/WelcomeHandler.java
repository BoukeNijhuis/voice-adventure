package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Request;

public class WelcomeHandler extends Handler {

    @Override
    public String updateState(Request request) {
        return "Hi! Welcome to Voice Adventure. You play this game like a text adventure, but instead of text, you use your voice. You can directly start with issuing commands or ask for more help.";
    }
}
