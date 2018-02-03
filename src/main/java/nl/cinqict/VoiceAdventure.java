package nl.cinqict;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.cinqict.handler.DefaultHandler;
import nl.cinqict.handler.Handler;
import nl.cinqict.handler.LookHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class VoiceAdventure implements RequestStreamHandler {

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {

        // get the request from the input stream
        final String request = readInputStream(inputStream);

        // get the request as a JsonObject
        JsonObject jsonObject = JsonUtil.getJsonObject(request);

        // get the intentName
        JsonObject result = jsonObject.getAsJsonObject("result");
        JsonObject metadata = result.getAsJsonObject("metadata");
        String intentName = metadata.get("intentName").getAsString();

        JsonArray contexts = result.getAsJsonArray("contexts");
        JsonObject context0 = contexts.get(0).getAsJsonObject();
        JsonObject parameters = context0.getAsJsonObject("parameters");
        String posx = parameters.get("posx").getAsString();
        parameters.addProperty("posx", posx + "x");


        Handler handler = getHandler(intentName);

        // write the reply on the output stream
        final String reply = createReply(handler.getReply(), context0);
        outputStream.write(reply.getBytes());
    }

    private Handler getHandler(String command) {

        switch (command) {
            case "LookIntent":
                return new LookHandler();
            case "MoveIntent":
                //return new MoveHandler();
            default:
                return new DefaultHandler();
        }
    }

    /**
     * Simple method to convert an InputStream to a String.
     *
     * @param inputStream the InputStream object to convert
     * @return the inputStream as String
     * @throws IOException when there is something wrong with the inputStream
     */
    private String readInputStream(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

        int character;
        while ((character = inputStreamReader.read()) != -1) {
            stringBuilder.append((char) character);
        }

        return stringBuilder.toString();
    }

    private String createReply(String input, JsonObject context) {
        JsonObject reply = new JsonObject();
        String answer = String.format("You said: %s", input);
        reply.addProperty("speech", answer);
        reply.addProperty("displayText", answer);
        reply.addProperty("data", "data");

        JsonArray contextOut = new JsonArray();
        contextOut.add(context);
        reply.add("contextOut", contextOut);
        reply.addProperty("source", "source");
        return reply.toString();
    }
}