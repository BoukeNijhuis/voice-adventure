package nl.cinqict.voiceadventure

import com.amazonaws.services.lambda.runtime.Context
import com.google.gson.JsonObject
import spock.lang.Specification
import spock.lang.Unroll

class IntentsTest extends Specification {

    @Unroll("#intent")
    void testIntents() {
        expect:
        getActualReply(intent).toString() == getExpectedReply(intent).toString()
        where:
        intent               | _
        "welcome"            | _
        "look-location"      | _
        "look-object"        | _
//        "move"               | _
        "pickup"             | _
        "use"                | _
        "use-unknown-object" | _
    }

    private JsonObject getExpectedReply(String folderName) {
        String expectedReplyFileName = folderName + "/expectedReply.json";
        return JsonUtil.getJsonObject(TestUtils.readFile(expectedReplyFileName));
    }

    private JsonObject getActualReply(String folderName) {

        String requestFileName = folderName + "/testRequest.json";
        try {
            // read the request from a file
            final String request = TestUtils.readFile(requestFileName);
            final InputStream inputStream = new ByteArrayInputStream(request.getBytes());
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final Context context = Mock()

            // getReply the request
            final VoiceAdventure voiceAdventure = new VoiceAdventure();
            voiceAdventure.handleRequest(inputStream, outputStream, context);

            // fetch the reply
            final String actualReply = new String(outputStream.toByteArray());

            return JsonUtil.getJsonObject(actualReply);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
