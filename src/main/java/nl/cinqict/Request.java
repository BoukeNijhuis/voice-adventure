package nl.cinqict;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static nl.cinqict.DialogflowConstants.*;

public class Request {

    private JsonObject result;
    private State state;
    private Parameters parameters;

    public Request(String request) {

        // get the request as a JsonObject
        JsonObject jsonObject = JsonUtil.getJsonObject(request);

        // get the intentName
        result = jsonObject.getAsJsonObject(RESULT);

        // get the parameters
        JsonObject parameters = result.getAsJsonObject(PARAMETERS);

        JsonArray contexts = result.getAsJsonArray(CONTEXTS);

        // get the stateContext
        for (JsonElement o : contexts) {
            final JsonObject context = o.getAsJsonObject();
            final JsonElement name = context.get(NAME);
            if (STATE.equals(name.getAsString()))
                state = new State(context.get(PARAMETERS).getAsJsonObject());
        }

        // the first time there will be no state, so initialize it
        if (state == null) state = new State();

        this.parameters = new Parameters(parameters);
    }

    public String getIntentName() {
        JsonObject metadata = result.getAsJsonObject(METADATA);
        return metadata.get(INTENT_NAME).getAsString();
    }

    public JsonObject getStateContext() {
        JsonObject stateContext = new JsonObject();
        stateContext.addProperty(NAME, STATE);
        stateContext.add(PARAMETERS, state.toJsonObject());
        stateContext.addProperty(LIFESPAN, 5);

        return stateContext;
    }

    public State getState() {
        return state;
    }

    public Parameters getParameters() {
        return parameters;
    }
}
