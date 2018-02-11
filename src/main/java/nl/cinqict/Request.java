package nl.cinqict;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Request {

    private JsonObject result;
    private State state;

    public Request(String request) {

        // get the request as a JsonObject
        JsonObject jsonObject = JsonUtil.getJsonObject(request);

        // get the intentName
        result = jsonObject.getAsJsonObject("result");

        JsonArray contexts = result.getAsJsonArray("contexts");

        // get the stateContext
        for (JsonElement o : contexts) {
            final JsonObject context = o.getAsJsonObject();
            final JsonElement name = context.get("name");
            if ("state".equals(name.getAsString()))
                state = new State(context.get("parameters").getAsJsonObject());
        }

        // the first time there will be no state, so initialize it
        if (state == null) state = new State();
    }

    public String getIntentName() {
        JsonObject metadata = result.getAsJsonObject("metadata");
        return metadata.get("intentName").getAsString();
    }

    public JsonObject getStateContext() {
        JsonObject stateContext = new JsonObject();
        stateContext.addProperty("name", "state");
        stateContext.add("parameters", state.toJsonObject());
        stateContext.addProperty("lifespan", 5);

        return stateContext;
    }

    public State getState() {
        return state;
    }
}
