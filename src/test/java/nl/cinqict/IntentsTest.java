package nl.cinqict;

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

public class IntentsTest {


    @Test
    public void welcome() {
        test("welcome");
    }

    @Test
    public void look() {
        test("look");
    }

    @Test
    public void move() {
        test("move");
    }

    private void test(String folderName) {
        test(folderName + "/testRequest.json", folderName + "/expectedReply.json");
    }


    private void test(String requestFileName, String expectedReplyFileName) {
        try {
            // read the request from a file
            final String request = readFile(requestFileName);
            final InputStream inputStream = new ByteArrayInputStream(request.getBytes());
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final Context context = Mockito.mock(Context.class);

            // getReply the request
            final VoiceAdventure voiceAdventure = new VoiceAdventure();
            voiceAdventure.handleRequest(inputStream, outputStream, context);

            // fetch the reply
            final String actualReply = new String(outputStream.toByteArray());
            final String expectedReply = readFile(expectedReplyFileName);

            final JsonObject actualResultReply = JsonUtil.getJsonObject(actualReply);
            final JsonObject expectedResultReply = JsonUtil.getJsonObject(expectedReply);
            assertEquals(expectedResultReply, actualResultReply);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simple method to read a file.
     *
     * @return the contents of the file
     * @throws IOException when the file cannot be read
     */
    private String readFile(String fileName) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes((Paths.get(ClassLoader.getSystemResource(fileName).toURI()))));
    }
}
