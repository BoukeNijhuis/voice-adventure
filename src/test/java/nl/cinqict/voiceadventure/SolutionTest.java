package nl.cinqict.voiceadventure;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.cinqict.voiceadventure.handler.Intent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static nl.cinqict.voiceadventure.DialogflowConstants.*;
import static nl.cinqict.voiceadventure.handler.Intent.*;
import static nl.cinqict.voiceadventure.world.Item.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SolutionTest {

    private ArrayList<Action> solution = new ArrayList<>();

    @Test
    void solution() throws IOException, URISyntaxException {

        //solution.add(new Action("?", WELCOME_INTENT));
        solution.add(new Action(MOVE_INTENT, DIRECTION, EAST));
        solution.add(new Action(PICKUP_INTENT, OBJECT, HANDLE.getName()));
        solution.add(new Action(MOVE_INTENT, DIRECTION, WEST));
        solution.add(new Action(MOVE_INTENT, DIRECTION, WEST));
        solution.add(new Action(USE_INTENT, OBJECT, HANDLE.getName(), OBJECT1, WELL.getName()));
        solution.add(new Action(MOVE_INTENT, DIRECTION, EAST));
        solution.add(new Action(MOVE_INTENT, DIRECTION, SOUTH));
        solution.add(new Action(USE_INTENT, OBJECT, SWORD.getName(), OBJECT1, TROLL.getName()));
        solution.add(new Action(MOVE_INTENT, DIRECTION, NORTH));
        solution.add(new Action(MOVE_INTENT, DIRECTION, NORTH));
        solution.add(new Action(USE_INTENT, OBJECT, KEY.getName(), OBJECT1, DOOR.getName()));

        String requestFormat = TestUtils.readFile("request-format.json");
        JsonArray contextOut = null;
        JsonObject responseObject = null;

        for (Action action : solution) {
            System.out.println(action.intent.toString() + ", " + action.parameter);
            // create the request (by filling in the resolvedQuery)
            String request = String.format(requestFormat, action.parameter, action.intent.getIntentName());

            // add contextOut (if any)
            if (contextOut != null) {
                final JsonObject requestWithoutContext = JsonUtil.getJsonObject(request);
                final JsonElement contexts = requestWithoutContext.get(QUERY_RESULT).getAsJsonObject().get(CONTEXTS);
                contexts.getAsJsonArray().addAll(contextOut);
                request = requestWithoutContext.toString();
            }

            final InputStream inputStream = new ByteArrayInputStream(request.getBytes());
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final Context context = Mockito.mock(Context.class);

            // handle the request
            final VoiceAdventure voiceAdventure = new VoiceAdventure();
            voiceAdventure.handleRequest(inputStream, outputStream, context);

            // fetch the reply
            final String response = new String(outputStream.toByteArray());
            responseObject = JsonUtil.getJsonObject(response);

            // print the reply
            final String speech = responseObject.get(FULFILLMENT_TEXT).getAsString();
            System.out.println(speech);

            // create next request format (by adding the contextOut as context in the request)
            contextOut = responseObject.getAsJsonArray(CONTEXT_OUT);
        }

        // assert that the last reply finishes the game
        assertNotNull(responseObject.get(FOLLOWUP_EVENT));
    }

    private class Action {

        Intent intent;
        String parameter;

        Action(Intent intent) {
            this(intent, "{}");
        }

        Action(Intent intent, String... parameterList) {
            this.intent = intent;

            // only one parameter, so use this parameter
            if (parameterList.length == 1) {
                this.parameter = parameterList[0];
            } else {
                if (parameterList.length % 2 != 0) {
                    throw new RuntimeException("Cannot create a parameter object with an odd amount of arguments");
                } else {
                    this.parameter = createParameterObject(parameterList);
                }
            }
        }

        private String createParameterObject(String[] parameterList) {

            // start the object
            StringBuilder result = new StringBuilder("{");

            for (int i = 0; i < parameterList.length; i++) {

                // put the parameter in quotes
                result.append(String.format("\"%s\"", parameterList[i]));
                // when not the last parameter
                if (i != parameterList.length - 1) {
                    // between key and value
                    if (i % 2 == 0) {
                        result.append(":");
                        // after value (prepare for next key value
                    } else {
                        result.append(",");
                    }
                }
            }
            // end the object
            result.append("}");

            return result.toString();
        }
    }
}
