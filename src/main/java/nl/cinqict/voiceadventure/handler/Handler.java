package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Request;

public abstract class Handler {

    String reply;
    boolean gameOver = false;

    public void updateState(Request request) {
        // do nothing
    }

    public String getReply() {
        return reply;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
