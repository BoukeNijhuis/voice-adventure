package nl.cinqict.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class WelcomeHandler extends Handler {

    @Override
    public String getReply() {
        return "Hi! Welcome to Voice Adventure. You can directly start with issuing commands or ask for help about commands.";
    }
}
