package nl.cinqict.handler;


import com.google.gson.JsonObject;
import nl.cinqict.State;

public class DefaultHandler extends Handler {

    @Override
    public String getReply() {
        return "Default reply!";
    }
}
