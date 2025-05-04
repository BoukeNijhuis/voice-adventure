package nl.cinqict.voiceadventure;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nl.cinqict.voiceadventure.handler.Handler;
import nl.cinqict.voiceadventure.handler.Intent;
import nl.cinqict.voiceadventure.message.Reply;
import nl.cinqict.voiceadventure.message.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class VoiceAdventure {

    // Store the state context between commands
    private static JsonObject stateContext = null;

    /**
     * Main method that allows users to input commands directly.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VoiceAdventure voiceAdventure = new VoiceAdventure();
        String session = "projects/voice-adventure/agent/sessions/123456789";
        boolean gameOver = false;

        System.out.println("Welcome to Voice Adventure!");
        System.out.println("Available commands: welcome, look, move [direction], pickup [object], use [object], kill [object], inventory");
        System.out.println("Type 'exit' to quit the game.");
        System.out.println();

        // Start with welcome intent
        processCommand(voiceAdventure, "welcome", null, null, session);

        while (!gameOver) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("Thanks for playing!");
                break;
            }

            String intent = parseIntent(input);
            String object = parseObject(input);
            String object2 = parseSecondObject(input);

            gameOver = processCommand(voiceAdventure, intent, object, object2, session);
        }

        scanner.close();
    }

    /**
     * Process a command and return whether the game is over.
     * 
     * @param voiceAdventure the VoiceAdventure instance
     * @param intent the intent name
     * @param object the primary object (can be null)
     * @param object2 the secondary object (can be null)
     * @param session the session ID
     * @return true if the game is over, false otherwise
     */
    public static boolean processCommand(VoiceAdventure voiceAdventure, String intent, String object, String object2, String session) {
        try {
            // Create a JSON string that mimics what Dialogflow would send
            String jsonRequest = createJsonRequest(intent, object, object2, session);

            // Create a Request object from this JSON
            Request request = new Request(jsonRequest);

            // Get the handler for this intent
            Handler handler = Intent.getIntent(request.getIntentName()).getHandler();

            // Execute the handler
            String fulfillmentText = handler.updateState(request);

            // Create a Reply object with the response
            Reply reply = new Reply(fulfillmentText, request.getStateContext(), handler.isGameOver());

            // Extract and display the fulfillment text
            JsonObject replyJson = JsonUtil.getJsonObject(reply.createReply());
            String responseText = replyJson.get(DialogflowConstants.FULFILLMENT_TEXT).getAsString();
            System.out.println(responseText);

            // Store the state context for the next command
            JsonArray contextOut = replyJson.getAsJsonArray(DialogflowConstants.CONTEXT_OUT);
            if (contextOut != null && contextOut.size() > 0) {
                stateContext = contextOut.get(0).getAsJsonObject();
            }

            return handler.isGameOver();
        } catch (Exception e) {
            System.out.println("I don't understand that command. Please try again.");
            return false;
        }
    }

    /**
     * Create a JSON string that mimics what Dialogflow would send.
     * 
     * @param intent the intent name
     * @param object the primary object (can be null)
     * @param object2 the secondary object (can be null)
     * @param session the session ID
     * @return a JSON string
     */
    private static String createJsonRequest(String intent, String object, String object2, String session) {
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty(DialogflowConstants.SESSION, session);

        JsonObject queryResult = new JsonObject();

        JsonObject intentObj = new JsonObject();
        intentObj.addProperty(DialogflowConstants.DISPLAY_NAME, intent);
        queryResult.add(DialogflowConstants.INTENT, intentObj);

        JsonObject parameters = new JsonObject();
        if (object != null && intent.equals(Intent.MOVE_INTENT.getIntentName())) {
            parameters.addProperty(DialogflowConstants.DIRECTION, object);
        } else {
            parameters.addProperty(DialogflowConstants.OBJECT, object);
        }
        if (object2 != null) {
            parameters.addProperty(DialogflowConstants.OBJECT1, object2);
        }
        queryResult.add(DialogflowConstants.PARAMETERS, parameters);

        // Add contexts array with state context if available
        JsonArray contexts = new JsonArray();
        if (stateContext != null) {
            contexts.add(stateContext);
        }
        queryResult.add(DialogflowConstants.CONTEXTS, contexts);

        jsonRequest.add(DialogflowConstants.QUERY_RESULT, queryResult);

        return jsonRequest.toString();
    }

    /**
     * Parse the intent from the user input.
     * 
     * @param input the user input
     * @return the intent name
     */
    private static String parseIntent(String input) {
        if (input.startsWith("welcome")) return "WelcomeIntent";
        if (input.startsWith("look")) return "LookIntent";
        if (input.startsWith("move")) return "MoveIntent";
        if (input.startsWith("pickup") || input.startsWith("pick up")) return "PickupIntent";
        if (input.startsWith("use")) return "UseIntent";
        if (input.startsWith("kill")) return "KillIntent";
        if (input.startsWith("inventory")) return "InventoryIntent";

        // Default to look intent if we can't determine the intent
        return "LookIntent";
    }

    /**
     * Parse the primary object from the user input.
     * 
     * @param input the user input
     * @return the object name or null if none
     */
    public static String parseObject(String input) {
        String[] words = input.split("\\s+");

        if (words.length < 2) return null;

        if (input.startsWith("move")) {
            String direction = words[1];
            if (direction.equals("north") || direction.equals("n")) return "N";
            if (direction.equals("east") || direction.equals("e")) return "E";
            if (direction.equals("south") || direction.equals("s")) return "S";
            if (direction.equals("west") || direction.equals("w")) return "W";
            return null;
        }

        // For other commands, the object is the second word
        return words[1].toUpperCase();
    }

    /**
     * Parse the secondary object from the user input.
     * 
     * @param input the user input
     * @return the second object name or null if none
     */
    public static String parseSecondObject(String input) {
        String[] words = input.split("\\s+");

        if (words.length < 4) return null;

        // For "use X on Y" commands, the second object is the fourth word
        if (input.startsWith("use") && words.length >= 4 && words[2].equals("on")) {
            return words[3].toUpperCase();
        }

        // For other commands, the second object is the third word
        return words[2].toUpperCase();
    }
}
