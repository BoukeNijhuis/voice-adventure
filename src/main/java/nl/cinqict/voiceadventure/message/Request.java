package nl.cinqict.voiceadventure.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.cinqict.voiceadventure.JsonUtil;
import nl.cinqict.voiceadventure.DialogflowConstants;

import static nl.cinqict.voiceadventure.DialogflowConstants.QUERY_RESULT;
import static nl.cinqict.voiceadventure.DialogflowConstants.SESSION;

public class Request {

    private JsonObject queryResult;
    private State state;
    private Parameters parameters;
    private final String session;
    private final String stateName;

    public Request(String request) {

        // get the request as a JsonObject
        JsonObject jsonObject = JsonUtil.getJsonObject(request);

        // get the session
        this.session = jsonObject.getAsJsonPrimitive(SESSION).getAsString();
        this.stateName = this.session + "/contexts/" + DialogflowConstants.STATE;

        // get the intentName
        queryResult = jsonObject.getAsJsonObject(QUERY_RESULT);

        // get the parameters
        JsonObject parameters = queryResult.getAsJsonObject(DialogflowConstants.PARAMETERS);

        JsonArray contexts = queryResult.getAsJsonArray(DialogflowConstants.CONTEXTS);

        // contexts are optional now
        if (contexts == null) {
            contexts = new JsonArray();
        }

        // get the stateContext
        for (JsonElement o : contexts) {
            final JsonObject context = o.getAsJsonObject();
            final JsonElement name = context.get(DialogflowConstants.NAME);
            if (this.stateName.equals(name.getAsString())) {
                state = new State(context.get(DialogflowConstants.PARAMETERS).getAsJsonObject());
            }
        }

        // the first time there will be no state, so initialize it
        if (state == null) state = new State();

        this.parameters = new Parameters(parameters);


    }

    public String getIntentName() {
        JsonObject metadata = queryResult.getAsJsonObject(DialogflowConstants.INTENT);
        return metadata.get(DialogflowConstants.DISPLAY_NAME).getAsString();
    }

    public JsonObject getStateContext() {
        JsonObject stateContext = new JsonObject();
        stateContext.addProperty(DialogflowConstants.NAME, this.stateName);
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
