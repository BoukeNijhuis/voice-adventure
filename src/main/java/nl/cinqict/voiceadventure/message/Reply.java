package nl.cinqict.voiceadventure.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static nl.cinqict.voiceadventure.DialogflowConstants.*;

public class Reply {

    private final String fulfillmentText;
    private final JsonObject contextObject;
    private final boolean isGameOver;

    public Reply(String fulfillmentText, JsonObject contextObject, boolean isGameOver) {
        this.fulfillmentText = fulfillmentText;
        this.contextObject = contextObject;
        this.isGameOver = isGameOver;
    }

    public String createReply() {
        JsonObject reply = new JsonObject();

        reply.addProperty(FULFILLMENT_TEXT, fulfillmentText);
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
