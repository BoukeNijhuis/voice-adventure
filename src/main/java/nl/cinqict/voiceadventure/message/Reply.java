package nl.cinqict.voiceadventure.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static nl.cinqict.voiceadventure.DialogflowConstants.*;

public class Reply {

    private String speech;
    private JsonObject contextObject;
    private boolean isGameOver;

    public Reply(String speech, JsonObject contextObject, boolean isGameOver) {
        this.speech = speech;
        this.contextObject = contextObject;
        this.isGameOver = isGameOver;
    }

    public String createReply() {
        JsonObject reply = new JsonObject();

        reply.addProperty(SPEECH, speech);
        reply.addProperty(DISPLAY_TEXT, speech);
        reply.addProperty(DATA, DATA);

        JsonArray contextOut = new JsonArray();
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
