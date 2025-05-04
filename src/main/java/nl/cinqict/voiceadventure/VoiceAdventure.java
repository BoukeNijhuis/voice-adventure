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

        // Start with welcome intent
        processCommand(voiceAdventure, "welcomeIntent", null, null, session);

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
        if (input.startsWith("move") || input.startsWith("go")) return "MoveIntent";
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

        if (input.startsWith("move") || input.startsWith("go")) {
            String direction = words[1];
            if (direction.equals("north") || direction.equals("n")) return "N";
            if (direction.equals("east") || direction.equals("e")) return "E";
            if (direction.equals("south") || direction.equals("s")) return "S";
            if (direction.equals("west") || direction.equals("w")) return "W";
            return null;
        }

        // Skip the command word (first word)
        int startIndex = 1;

        // Handle "pick up" as a single command
        if (input.startsWith("pick up") && words.length >= 3) {
            startIndex = 2;
        }

        // Skip articles and other common filler words
        for (int i = startIndex; i < words.length; i++) {
            String word = words[i];

            // Skip articles and other common filler words
            if (word.equals("the") || word.equals("a") || word.equals("an") || 
                word.equals("on") || word.equals("at") || word.equals("with")) {
                continue;
            }

            // Return the word as the object
            return word.toUpperCase();
        }

        // If we get here, we didn't find a valid object, so fall back to the original behavior
        if (input.startsWith("pick up") && words.length >= 3) {
            return words[2].toUpperCase();
        } else {
            return words[startIndex].toUpperCase();
        }
    }

    /**
     * Parse the secondary object from the user input.
     * 
     * @param input the user input
     * @return the second object name or null if none
     */
    public static String parseSecondObject(String input) {
        String[] words = input.split("\\s+");

        if (words.length < 3) return null;

        // For "use X on Y" commands
        if (input.startsWith("use")) {
            // Find the index of "on" in the command
            int onIndex = -1;
            for (int i = 2; i < words.length; i++) {
                if (words[i].equals("on")) {
                    onIndex = i;
                    break;
                }
            }

            // If "on" is found, look for the object after it
            if (onIndex != -1 && onIndex + 1 < words.length) {
                // Skip articles and other common filler words
                for (int i = onIndex + 1; i < words.length; i++) {
                    String word = words[i];

                    // Skip articles and other common filler words
                    if (word.equals("the") || word.equals("a") || word.equals("an") || 
                        word.equals("at") || word.equals("with")) {
                        continue;
                    }

                    // Return the word as the object
                    return word.toUpperCase();
                }
            }
        }

        // For other commands, find the second object after the first object
        // First, find the index of the first object
        String firstObject = parseObject(input);
        if (firstObject == null) return null;

        int firstObjectIndex = -1;
        for (int i = 0; i < words.length; i++) {
            if (words[i].toUpperCase().equals(firstObject)) {
                firstObjectIndex = i;
                break;
            }
        }

        // If the first object is found, look for the second object after it
        if (firstObjectIndex != -1 && firstObjectIndex + 1 < words.length) {
            // Skip articles and other common filler words
            for (int i = firstObjectIndex + 1; i < words.length; i++) {
                String word = words[i];

                // Skip articles and other common filler words
                if (word.equals("the") || word.equals("a") || word.equals("an") || 
                    word.equals("on") || word.equals("at") || word.equals("with")) {
                    continue;
                }

                // Return the word as the object
                return word.toUpperCase();
            }
        }

        // If we get here, we didn't find a valid second object
        return null;
    }
}
