package nl.cinqict;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LambaProxyUtil {

    private static JsonParser jsonParser = new JsonParser();

    public static String createLambdaProxyReply(String body) {

        JsonObject reply = new JsonObject();
        reply.addProperty("statusCode", 200);

        JsonObject headers = new JsonObject();
        headers.addProperty("Content-Type","application/json");
        reply.add("headers", headers);

        reply.addProperty("body", body);

        reply.addProperty("isBase64Encoded", false);

        return reply.toString();
    }

    public static JsonObject getBodyJsonObject(String jsonString) {
        return getJsonObject(jsonString, "body");
    }

    private static JsonObject getJsonObject(String jsonString, String element) {
        // get the request as a JsonObject
        JsonObject jsonObject = getJsonObject(jsonString);

        // get the body from the lambda proxy request
        final String elementString = jsonObject.get(element).getAsString();
        return LambaProxyUtil.getJsonObject(elementString);
    }

    private static JsonObject getJsonObject(String jsonString) {
        return jsonParser.parse(jsonString).getAsJsonObject();
    }
}
