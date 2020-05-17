package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Request;

public abstract class Handler {

    protected boolean gameOver = false;

    public String updateState(Request request) {
        return "the defaultHandler updateState method should implemented!";
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
