package nl.cinqict;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nl.cinqict.handler.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

import static nl.cinqict.DialogflowConstants.*;

public class VoiceAdventure implements RequestStreamHandler {

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {

        // get the requestString from the input stream
        final String requestString = readInputStream(inputStream);

        //System.out.print("REQUEST: " + requestString);

        // create request object
        final Request request = new Request(requestString);

        // get the intentName
        final String intentName = request.getIntentName();

        // get the parameter object
        //final JsonObject stateParameters = request.getStateParameters();

        Handler handler = getHandler(intentName);
        handler.updateState(request);

        // write the reply on the output stream
        final String reply = createReply(handler.getReply(), request.getStateContext());

        //System.out.println("REPLY: " + reply);

        outputStream.write(reply.getBytes());
    }


    private Handler getHandler(String command) {

        switch (command) {
            case "Default Welcome Intent":
                return new WelcomeHandler();
            case "LookIntent":
                return new LookHandler();
            case "MoveIntent":
                return new MoveHandler();
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

    private String createReply(String input, JsonObject context0) {
        JsonObject reply = new JsonObject();

        reply.addProperty(SPEECH, input);
        reply.addProperty(DISPLAY_TEXT, input);
        reply.addProperty(DATA, "data");

        JsonArray contextOut = new JsonArray();
        contextOut.add(context0);
        reply.add(CONTEXT_OUT, contextOut);
        reply.addProperty(SOURCE, "source");
        return reply.toString();
    }
}