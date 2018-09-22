package fileIO;

import org.json.JSONObject;
import simulator.Simulator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Load {

    public static void loadFromFile(String fileName) {

        try {
            String fileText = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
