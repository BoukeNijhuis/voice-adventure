package nl.cinqict.voiceadventure

import com.amazonaws.services.lambda.runtime.Context
import com.google.gson.JsonObject
import spock.lang.Specification

class IntentsTest extends Specification {

    void welcome() {
        expect:
        test("welcome");
    }


    void lookLocation() {
        expect:
        test("look-location");
    }


    void lookObject() {
        expect:
        test("look-object");
    }


    void move() {
        expect:
        test("move");
    }


    void pickup() {
        expect:
        test("pickup");
    }


    void use() {
        expect:
        test("use");
    }


    void useUnknownObject() {
        expect:
        test("use-unknown-object");
    }

    private void test(String folderName) {
        test(folderName + "/testRequest.json", folderName + "/expectedReply.json");
    }


    private boolean test(String requestFileName, String expectedReplyFileName) {
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
            final String expectedReply = TestUtils.readFile(expectedReplyFileName);

            final JsonObject actualResultReply = JsonUtil.getJsonObject(actualReply);
            final JsonObject expectedResultReply = JsonUtil.getJsonObject(expectedReply);
            return expectedResultReply == actualResultReply;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
