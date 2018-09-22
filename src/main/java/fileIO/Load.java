package fileIO;

import java.io.*;
import java.util.stream.Collectors;

public class Load {

    public static void loadFromFile(String fileName) {
        loadTextFromFile(fileName);
    }

    static String loadTextFromFile(String fileName) {
        InputStream inputStream = Load.class.getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return reader.lines().collect(Collectors.joining());
    }
}
