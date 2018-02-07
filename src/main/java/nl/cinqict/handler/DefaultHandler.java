package nl.cinqict.handler;


import com.google.gson.JsonObject;

public class DefaultHandler extends Handler {

    @Override
    public void updateState(JsonObject stateParameters) {
        // do nothing
    }

    @Override
    public String getReply() {
        return "Default reply!";
    }
}
