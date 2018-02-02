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

public class VoiceAdventureTest {

    @Test
    public void test() throws IOException, URISyntaxException {

        // read the request from a file
        final String request = readFile("testRequest.json");
        final InputStream inputStream = new ByteArrayInputStream( request.getBytes() );
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final Context context = Mockito.mock(Context.class);

        // handle the request
        final VoiceAdventure voiceAdventure = new VoiceAdventure();
        voiceAdventure.handleRequest(inputStream, outputStream, context);

        // fetch the reply
        final String actualReply = new String(outputStream.toByteArray());
        final String expectedReply = readFile("expectedReply.json");

        final JsonObject actualResultReply = JsonUtil.getJsonObject(actualReply);
        final JsonObject expectedResultReply = JsonUtil.getJsonObject(expectedReply);
        assertEquals(expectedResultReply, actualResultReply);
    }

    /**
     * Simple method to read a file.
     * @return the contents of the file
     * @throws IOException when the file cannot be read
     */
    private String readFile(String fileName) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes((Paths.get(ClassLoader.getSystemResource(fileName).toURI()))));
    }
}
