package nl.cinqict.voiceadventure;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntentsTest {

    @Test
    void welcome() {
        test("welcome");
    }

    @Test
    void lookLocation() {
        test("look-location");
    }

    @Test
    void lookObject() {
        test("look-object");
    }

    @Test
    void move() {
        test("move");
    }

    @Test
    void pickup() {
        test("pickup");
    }

    @Test
    void use() {
        test("use");
    }

    @Test
    void useUnknownObject() {
        test("use-unknown-object");
    }

    private void test(String folderName) {
        test(folderName + "/testRequest.json", folderName + "/expectedReply.json");
    }


    private void test(String requestFileName, String expectedReplyFileName) {
        try {
            // read the request from a file
            final String request = TestUtils.readFile(requestFileName);
            final InputStream inputStream = new ByteArrayInputStream(request.getBytes());
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final Context context = Mockito.mock(Context.class);

            // getReply the request
            final VoiceAdventure voiceAdventure = new VoiceAdventure();
            voiceAdventure.handleRequest(inputStream, outputStream, context);

            // fetch the reply
            final String actualReply = new String(outputStream.toByteArray());
            final String expectedReply = TestUtils.readFile(expectedReplyFileName);

            final JsonObject actualResultReply = JsonUtil.getJsonObject(actualReply);
            final JsonObject expectedResultReply = JsonUtil.getJsonObject(expectedReply);
            assertEquals(expectedResultReply, actualResultReply);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
