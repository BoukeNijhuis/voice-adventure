package nl.cinqict;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

        // get the resolvedQuery
        JsonObject result = jsonObject.getAsJsonObject("result");
        String resolvedQuery = result.get("resolvedQuery").getAsString();

            // write the reply on the output stream
        final String reply = createReply(resolvedQuery);
        outputStream.write(reply.getBytes());
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

    private String createReply(String input) {
        JsonObject reply = new JsonObject();
        String answer = String.format("You said: %s", input);
        reply.addProperty("speech", answer);
        reply.addProperty("displayText", answer);
        reply.addProperty("data", "data");
        reply.add("contextOut", new JsonArray());
        reply.addProperty("source", "source");
        return reply.toString();
    }
}