package nl.cinqict.voiceadventure.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static nl.cinqict.voiceadventure.DialogflowConstants.*;

public class Reply {

    private String fullfillmentText;
    private JsonObject contextObject;
    private boolean isGameOver;

    public Reply(String fullfillmentText, JsonObject contextObject, boolean isGameOver) {
        this.fullfillmentText = fullfillmentText;
        this.contextObject = contextObject;
        this.isGameOver = isGameOver;
    }

    public String createReply() {
        JsonObject reply = new JsonObject();

        reply.addProperty(FULFILLMENT_TEXT, fullfillmentText);
        reply.add(PAYLOAD, new JsonObject());

        JsonArray contextOut = new JsonArray();

        // todo: fix the name of context name
        contextOut.add(contextObject);

        reply.add(CONTEXT_OUT, contextOut);
        reply.addProperty(SOURCE, VOICE_ADVENTURE);

        if (isGameOver) {
            JsonObject followUpEvent = new JsonObject();
            followUpEvent.addProperty(NAME, END_EVENT);
            reply.add(FOLLOWUP_EVENT, followUpEvent);
        }

        return reply.toString();
    }
}
