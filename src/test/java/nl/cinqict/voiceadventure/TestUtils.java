package nl.cinqict.voiceadventure;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {

    /**
     * Simple method to read a file.
     *
     * @return the contents of the file
     * @throws IOException when the file cannot be read
     */
    public static String readFile(String fileName) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes((Paths.get(ClassLoader.getSystemResource(fileName).toURI()))));
    }
}
