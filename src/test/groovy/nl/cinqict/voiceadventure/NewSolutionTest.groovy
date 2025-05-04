package nl.cinqict.voiceadventure

import com.amazonaws.services.lambda.runtime.Context
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import nl.cinqict.voiceadventure.handler.Intent
import spock.lang.Specification

import static nl.cinqict.voiceadventure.DialogflowConstants.*
import static nl.cinqict.voiceadventure.handler.Intent.*
import static nl.cinqict.voiceadventure.world.Item.*

class NewSolutionTest extends Specification {

    private ArrayList<String> solution = new ArrayList<>();

    void solution() throws IOException, URISyntaxException {

        // create the list of actions that is the solution
        solution.add("move east");
        solution.add("pickup handle");
        solution.add("move west");
        solution.add("move west");
        solution.add("use handle on well");
        solution.add("move east");
        solution.add("move south");
        solution.add("use sword on troll");
        solution.add("move north");
        solution.add("move north");
        solution.add("use key on door");

        VoiceAdventure voiceAdventure = new VoiceAdventure();

        boolean gameOver

        for (String input : solution) {

            println input

            String intent = VoiceAdventure.parseIntent(input);
            String object = VoiceAdventure.parseObject(input);
            String object2 = VoiceAdventure.parseSecondObject(input);

            // State is now maintained between commands
            gameOver = VoiceAdventure.processCommand(voiceAdventure, intent, object, object2, "session");
        }

        // assert that the last reply finishes the game
        expect:
        gameOver == true
    }

}
