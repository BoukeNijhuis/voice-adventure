package nl.cinqict;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Request {

    private JsonObject jsonObject;
    private JsonObject result;
    private JsonObject stateContext;

    public Request(String request) {

        // get the request as a JsonObject
        jsonObject = JsonUtil.getJsonObject(request);

        // get the intentName
        result = jsonObject.getAsJsonObject("result");

        // get the stateContext
        JsonArray contexts = result.getAsJsonArray("contexts");

        for (JsonElement o : contexts) {
            final JsonElement name = o.getAsJsonObject().get("name");
            if ("state".equals(name.getAsString()))
                stateContext = o.getAsJsonObject();
        }
    }

    public String getIntentName() {
        JsonObject metadata = result.getAsJsonObject("metadata");
        return metadata.get("intentName").getAsString();
    }

    public JsonObject getStateParameters() {
        return stateContext.getAsJsonObject("parameters");
    }

    public JsonObject getStateContext() {
        return stateContext;
    }
}
