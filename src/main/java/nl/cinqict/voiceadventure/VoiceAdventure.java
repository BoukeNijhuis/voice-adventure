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
import java.nio.charset.Charset;

public class VoiceAdventure implements RequestStreamHandler {

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {

        // get the requestString from the input stream
        final String requestString = readInputStream(inputStream);

        // create request object
        final Request request = new Request(requestString);

        // get the intentName
        final String intentName = request.getIntentName();

        Handler handler = getHandler(intentName);
        handler.updateState(request);

        // write the reply on the output stream
        final Reply reply = new Reply(handler.getReply(), request.getStateContext(), handler.isGameOver());
        final String replyAsString = reply.createReply();

        outputStream.write(replyAsString.getBytes());
    }


    private Handler getHandler(String command) {

        // TODO: what is in my inventory handler?

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
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

        int character;
        while ((character = inputStreamReader.read()) != -1) {
            stringBuilder.append((char) character);
        }

        return stringBuilder.toString();
    }


}