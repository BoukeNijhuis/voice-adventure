package nl.cinqict.voiceadventure.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.cinqict.voiceadventure.DialogflowConstants;
import nl.cinqict.voiceadventure.JsonUtil;

import static nl.cinqict.voiceadventure.DialogflowConstants.QUERY_RESULT;
import static nl.cinqict.voiceadventure.DialogflowConstants.SESSION;

public class Request {

    private final JsonObject queryResult;
    private final State state;
    private final Parameters parameters;
    private final String stateName;

    public Request(String request) {

        // get the request as a JsonObject
        JsonObject jsonObject = JsonUtil.getJsonObject(request);

        // get the session
        String session = jsonObject.getAsJsonPrimitive(SESSION).getAsString();
        this.stateName = session + "/contexts/" + DialogflowConstants.STATE;

        // get the intentName
        queryResult = jsonObject.getAsJsonObject(QUERY_RESULT);

        // get the parameters
        JsonObject parameters = queryResult.getAsJsonObject(DialogflowConstants.PARAMETERS);
        JsonArray contexts = JsonUtil.getAsJsonArrayNullSafe(queryResult, DialogflowConstants.CONTEXTS);

        this.state = getState(contexts);
        this.parameters = new Parameters(parameters);
    }

    private State getState(JsonArray contexts) {
        // get the stateContext
        for (JsonElement o : contexts) {
            final JsonObject context = o.getAsJsonObject();
            final JsonElement name = context.get(DialogflowConstants.NAME);
            if (this.stateName.equals(name.getAsString())) {
                return new State(context.get(DialogflowConstants.PARAMETERS).getAsJsonObject());
            }
        }

        // the first time there will be no state, so initialize it
        return new State();
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
