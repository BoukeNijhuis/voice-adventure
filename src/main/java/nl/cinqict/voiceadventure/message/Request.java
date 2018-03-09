package nl.cinqict.voiceadventure.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.cinqict.voiceadventure.JsonUtil;
import nl.cinqict.voiceadventure.DialogflowConstants;

public class Request {

    private JsonObject result;
    private State state;
    private Parameters parameters;

    public Request(String request) {

        // get the request as a JsonObject
        JsonObject jsonObject = JsonUtil.getJsonObject(request);

        // get the intentName
        result = jsonObject.getAsJsonObject(DialogflowConstants.RESULT);

        // get the parameters
        JsonObject parameters = result.getAsJsonObject(DialogflowConstants.PARAMETERS);

        JsonArray contexts = result.getAsJsonArray(DialogflowConstants.CONTEXTS);

        // get the stateContext
        for (JsonElement o : contexts) {
            final JsonObject context = o.getAsJsonObject();
            final JsonElement name = context.get(DialogflowConstants.NAME);
            if (DialogflowConstants.STATE.equals(name.getAsString())) {
                state = new State(context.get(DialogflowConstants.PARAMETERS).getAsJsonObject());
            }
        }

        // the first time there will be no state, so initialize it
        if (state == null) state = new State();

        this.parameters = new Parameters(parameters);
    }

    public String getIntentName() {
        JsonObject metadata = result.getAsJsonObject(DialogflowConstants.METADATA);
        return metadata.get(DialogflowConstants.INTENT_NAME).getAsString();
    }

    public JsonObject getStateContext() {
        JsonObject stateContext = new JsonObject();
        stateContext.addProperty(DialogflowConstants.NAME, DialogflowConstants.STATE);
        stateContext.add(DialogflowConstants.PARAMETERS, state.toJsonObject());
        stateContext.addProperty(DialogflowConstants.LIFESPAN, 5);

        return stateContext;
    }

    public State getState() {
        return state;
    }

    public Parameters getParameters() {
        return parameters;
    }
}
