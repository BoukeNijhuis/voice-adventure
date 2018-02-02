package nl.cinqict;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {

    private static final JsonParser jsonParser = new JsonParser();

    public static JsonObject getJsonObject(String jsonString) {
        return jsonParser.parse(jsonString).getAsJsonObject();
    }
}
