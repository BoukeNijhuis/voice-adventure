package nl.cinqict.voiceadventure;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import nl.cinqict.voiceadventure.handler.Handler;
import nl.cinqict.voiceadventure.handler.Intent;
import nl.cinqict.voiceadventure.message.Reply;
import nl.cinqict.voiceadventure.message.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class VoiceAdventure implements RequestStreamHandler {

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {

        // create a request object from the input stream
        final Request request = new Request(readInputStream(inputStream));

        // get the handler for this intent
        Handler handler = getHandler(request.getIntentName());

        // execute the handler
        String fulfillmentText = handler.updateState(request);

        // write the reply on the output stream
        final Reply reply = new Reply(fulfillmentText, request.getStateContext(), handler.isGameOver());
        final String replyAsString = reply.createReply();

        outputStream.write(replyAsString.getBytes());
    }

    private Handler getHandler(String command) {
        return Intent.getIntent(command).getHandler();
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
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        int character;
        while ((character = inputStreamReader.read()) != -1) {
            stringBuilder.append((char) character);
        }

        return stringBuilder.toString();
    }


}
