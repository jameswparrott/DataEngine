package Engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    private Utils() {

    }

    public static String readFile(String path) {
        String result;
        try {
            result = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file [" + path + "] exception: " + e);
        }
        return result;
    }
}
